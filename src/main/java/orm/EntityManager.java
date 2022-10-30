package orm;

import orm.anotations.Entity;

import java.sql.Connection;
import java.sql.SQLException;

public class EntityManager<E> implements DBContext {
    private final Connection connection;

    public EntityManager(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean persist(E entity) throws SQLException {
        String tableName = this.getTableName(entity);
        String fieldList = this.getDBFieldsWithoutIdentity(entity);
        String valueList = this.getInsertValues(entity);

        String sql = String.format("INSERT INTO %s (%s) VALUES (%s)",
                tableName, fieldList, valueList);

        return this.connection.prepareStatement(sql).execute();
    }

    @Override
    public boolean persist(Object entity) throws SQLException {
        return false;
    }

    @Override
    public Iterable<E> find() {
        return null;
    }

    @Override
    public Iterable<E> find(String where) {
        return null;
    }

    @Override
    public Iterable<E> findFirst() {
        return null;
    }

    @Override
    public Iterable<E> findFirst(String where) {
        return null;
    }

    private String getTableName(E entity) {
       Entity annotation = entity.getClass().getAnnotation(Entity.class);

        if (annotation == null) {
            throw new ORMException("Provided class is not have Entity annotation");
        }
        return annotation.name();
    }
}
