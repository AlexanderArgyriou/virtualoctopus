package com.virtualoctopus;

import com.virtualoctopus.annotations.bean.definitions.VirtualOctopusRepository;
import com.virtualoctopus.annotations.dbase.VirtualOctopusQuery;

@VirtualOctopusRepository
public interface TestRepo {
    @VirtualOctopusQuery(query = "SELECT * from books")
    Object getAll();

    @VirtualOctopusQuery(query = "INSERT INTO books (title, author) VALUES ('book.title', 'book.author');")
    Object add(Book book);
}
