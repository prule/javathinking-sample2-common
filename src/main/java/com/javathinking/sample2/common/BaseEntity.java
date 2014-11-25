package com.javathinking.sample2.common;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.io.Serializable;
import java.util.List;


@MappedSuperclass
public abstract class BaseEntity implements AuditableEntity, Serializable {
    @Version
    protected int version;
    @Column(name = "MODIFIED_BY", nullable = true)
    protected String modifiedBy;
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Column(name = "MODIFIED_DATE", nullable = true)
    protected DateTime modifiedDate;
    @Column(name = "CREATED_BY", nullable = true)
    protected String createdBy;
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Column(name = "CREATED_DATE", nullable = true)
    protected DateTime createdDate;

    public abstract long getId();

    public abstract void setId(long id);

    public void setAuditFields() {
        String username = getUsername();
        if (createdBy == null) {
            createdBy = username;
        }
        if (createdDate == null) {
            createdDate = new DateTime();
        }

        modifiedDate = new DateTime();
        modifiedBy = username;
    }

    protected String getUsername() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null) {
//            Object principal = authentication.getPrincipal();
//            if (principal instanceof User) {
//                return ((User) principal).getUsername();
//            } else {
//                return (String) principal;
//            }
//        }
        return null;
    }


    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public DateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(DateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public DateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(DateTime createdDate) {
        this.createdDate = createdDate;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseEntity that = (BaseEntity) o;

        if (getId() != that.getId()) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (getId() ^ (getId() >>> 32));
    }

    public void inflate() {

    }

    public boolean isNew() {
        return !isNotNew();
    }

    public boolean isNotNew() {
        return getId() > 0;
    }

    public static void inflateAll(List<Object[]> list) {
        for (Object[] entity : list) {
            ((BaseEntity) entity[0]).inflate();
        }
    }

    public String[] getChangeLogFieldNames() {
        return null;
    }

}
