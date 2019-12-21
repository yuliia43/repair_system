package services.repositoryServices;

import org.apache.log4j.Logger;
import repositories.Repository;

import java.sql.SQLException;
import java.util.List;
/**
 * @author Yuliia Shcherbakova ON 06.12.2019
 * @project repair_system
 */
public class RepositoryService<T> {

    protected Repository<T> repository;
    private Logger logger = Logger.getLogger(Repository.class);

    public RepositoryService(Repository<T> repository) {
        this.repository = repository;
    }

    public void add(T item) throws SQLException{
        repository.add(item);
        logger.info("Added item to table " + item.getClass().toString());
    }

    public void update(T item) throws SQLException{
        repository.update(item);
        logger.info("Updated item to table " + item.getClass().toString());
    }

    public List<T> getAll() throws SQLException{
        return repository.getAll();
    }

    public T getOneById(int id) throws SQLException{
        return repository.getOneById(id);
    }
}
