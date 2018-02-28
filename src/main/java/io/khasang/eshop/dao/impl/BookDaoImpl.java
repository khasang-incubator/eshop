package io.khasang.eshop.dao.impl;

import io.khasang.eshop.dao.BookDao;
import io.khasang.eshop.entity.Book;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.springframework.transaction.annotation.Transactional;

import javax.transaction.Transaction;

public class BookDaoImpl extends BasicDaoImpl<Book> implements BookDao {
    public BookDaoImpl(Class<Book> entityClass) {
        super(entityClass);
    }
}
