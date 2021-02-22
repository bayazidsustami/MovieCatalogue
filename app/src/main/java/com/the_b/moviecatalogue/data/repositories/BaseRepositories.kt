package com.the_b.moviecatalogue.data.repositories

abstract class BaseRepositories<DataSource> {
    protected var remoteDataSource: DataSource? = null

    fun init(remoteDataSource: DataSource){
        this.remoteDataSource = remoteDataSource
    }
}