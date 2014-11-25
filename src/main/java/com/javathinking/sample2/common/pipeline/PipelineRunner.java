package com.javathinking.sample2.common.pipeline;

import com.google.common.base.Stopwatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Date: 12/03/2014
 */
public class PipelineRunner<T extends PipelineContext> {
    private static final Logger log = LoggerFactory.getLogger(PipelineRunner.class);

    private List<PipelineTask> tasks = new ArrayList();
    private final TransactionTemplate transactionTemplate;

    public PipelineRunner(PlatformTransactionManager transactionManager) {
        if (transactionManager != null) {
            this.transactionTemplate = new TransactionTemplate(transactionManager);
        } else {
            this.transactionTemplate = null;
        }
    }

    public void run(final T context, final Map data) {
        Stopwatch pipelineStopWatch = Stopwatch.createStarted();

        log.debug("Starting pipeline");
        try {
            for (final PipelineTask task : tasks) {
                boolean result;

                if (transactionTemplate != null) {
                    // run each task in its own transaction so results are available to subsequent transactions
                    // this helps if you are mixing jpa and sql
                    TransactionCallback<Boolean> action = new TransactionCallback<Boolean>() {
                        public Boolean doInTransaction(TransactionStatus status) {
                            return executeTask(task, context, data);
                        }
                    };
                    result = transactionTemplate.execute(action);
                } else {
                    result = executeTask(task, context, data);
                }

                if (!result) {
                    log.debug("Aborting pipeline");
                    break;
                }
            }
        } catch (Exception e) {
            // TODO handle this properly
            context.setResult(PipelineContext.Result.Fail);
            context.setException(e);
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            log.debug("Finished pipeline in " + pipelineStopWatch.elapsed(TimeUnit.SECONDS) + " seconds");
        }
    }

    private boolean executeTask(PipelineTask task, T context, Map data) {
        Stopwatch taskStopWatch = Stopwatch.createUnstarted();
        taskStopWatch.reset();
        taskStopWatch.start();
        try {
            return task.execute(context, data);
        } catch (Exception e) {
            throw new PipelineErrorException(e);
        } finally {
            taskStopWatch.stop();
            log.debug("Task " + task.getName() + " took " + taskStopWatch.elapsed(TimeUnit.SECONDS) + " seconds");
        }
    }

    public void add(PipelineTask task) {
        tasks.add(task);
    }
}
