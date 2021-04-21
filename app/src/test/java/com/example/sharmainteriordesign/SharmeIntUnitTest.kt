package com.example.sharmainteriordesign

import com.example.sharmainteriordesign.api.ServiceBuilder
import com.example.sharmainteriordesign.repository.ProductRepository
import com.example.sharmainteriordesign.repository.UserRepository
import com.example.sharmainteriordesign.roomdatabase.entity.User
import com.example.sharmainteriordesign.ui.model.Product
import junit.framework.Assert
import kotlinx.coroutines.runBlocking
import org.junit.Test

class SharmeIntUnitTest {
    private var repository=UserRepository()
    @Test
    fun checkRegisterUSer() = runBlocking {
        val user =
            User(name = "this is test", email = "thisisemail1212cx@gmail.com", password = "testing12")
        repository = UserRepository()
        val expectedResult = false

        val response = repository.registerUSer(user)
        val actualResult = response.success!!
        Assert.assertEquals(expectedResult, actualResult)
    }
    @Test
    fun loginUser() = runBlocking {
        val user = User(email = "binod@gmail.com", password = "binod")
        repository = UserRepository()
        val expectedResult = true
        val response = repository.checkUSer(user)
        val actualResult = response.success
        Assert.assertEquals(expectedResult, actualResult)
    }
    @Test
    fun getme() = runBlocking {
        val user = User(email = "binod@gmail.com", password = "binod")
        repository = UserRepository()
        val expectedResult = true
        val response = repository.checkUSer(user)
        ServiceBuilder.token = "Bearer " + response.token
        ServiceBuilder.id = response.id
        if (response.success == true) {
            val actualResult = repository.getMe(ServiceBuilder.id!!).success
            Assert.assertEquals(expectedResult, actualResult)
        }
    }
    @Test
    fun updateUser() = runBlocking {
        val user = User(email = "binod@gmail.com", password = "binod")
        repository = UserRepository()
        val expectedResult = true
        val response = repository.checkUSer(user)
        ServiceBuilder.token = "Bearer " + response.token
        ServiceBuilder.id = response.id
        val actualResult = repository.updateUser(User(_id=ServiceBuilder.id!!,name = "Gopal Thapa",email = "gopal@gmail.com",password = "gopal123")).success
        Assert.assertEquals(expectedResult, actualResult)
    }
    //---------------ProudctApi--------------//
    @Test
    fun uploadnote()= runBlocking {
        val user = User(email = "binod@gmail.com", password = "binod")
        repository = UserRepository()
        val expectedResult = true
        val response = repository.checkUSer(user)
        ServiceBuilder.token = "Bearer " + response.token
        ServiceBuilder.id = response.id
        val repo=ProductRepository()
        val actualResult=repo.insertProduct(Product(userId = ServiceBuilder.id!!,area = "testing",location = "nofile",price = "testing",phNo = "testing")).success
        Assert.assertEquals(expectedResult, actualResult)
    }
    @Test
    fun getAllNotes()= runBlocking {
        val user = User(email = "binod@gmail.com", password = "binod")
        repository = UserRepository()
        val expectedResult = true
        val response = repository.checkUSer(user)
        ServiceBuilder.token = "Bearer " + response.token
        ServiceBuilder.id = response.id
        val repo=ProductRepository()
        val actualResult=repo.getallProduct().success
        Assert.assertEquals(expectedResult, actualResult)
    }
    @Test
    fun getAllbookmarkedNotes()= runBlocking {
        val user = User(email = "binod@gmail.com", password = "binod")
        repository = UserRepository()
        val expectedResult = false
        val response = repository.checkUSer(user)
        ServiceBuilder.token = "Bearer " + response.token
        ServiceBuilder.id = response.id
        val repo=ProductRepository()
        val actualResult=repo.getinter(ServiceBuilder.id!!).success
        Assert.assertEquals(expectedResult, actualResult)
    }
//    @Test
//    fun rateNote()= runBlocking {
//        val user = User(email = "gopal@gmail.com", password = "gopal123")
//        repository = UserRepository()
//        val expectedResult = true
//        val response = repository.checkUSer(user)
//        ServiceBuilder.token = "Bearer " + response.token
//        ServiceBuilder.id = response.id
//        val repo=NoteRepository()
//        val actualResult=repo.RateNote(id = "6034cf1a6857a9b7c859990a",noofRating = "3",ratting = "4").success
//        Assert.assertEquals(expectedResult, actualResult)
//    }
//    @Test
//    fun getOwnNotes()= runBlocking {
//        val user = User(email = "gopal@gmail.com", password = "gopal123")
//        repository = UserRepository()
//        val expectedResult = true
//        val response = repository.checkUSer(user)
//        ServiceBuilder.token = "Bearer " + response.token
//        ServiceBuilder.id = response.id
//        val repo=NoteRepository()
//        val actualResult=repo.getOwnNotes(ServiceBuilder.id!!).success
//        Assert.assertEquals(expectedResult, actualResult)
//    }
//    //----------------BookMarkApi--------------//
//    @Test
//    fun getAllBookMarkedNotes()= runBlocking {
//        val user = User(email = "gopal@gmail.com", password = "gopal123")
//        repository = UserRepository()
//        val expectedResult = true
//        val response = repository.checkUSer(user)
//        ServiceBuilder.token = "Bearer " + response.token
//        ServiceBuilder.id = response.id
//        val repo=BookmarkRepository()
//        val actualResult=repo.getallbookmarkedNotes(ServiceBuilder.id!!).success
//        Assert.assertEquals(expectedResult, actualResult)
//    }
//    @Test
//    fun bookmarkTheNote()= runBlocking {
//        val user = User(email = "gopal@gmail.com", password = "gopal123")
//        repository = UserRepository()
//        val expectedResult = true
//        val response = repository.checkUSer(user)
//        ServiceBuilder.token = "Bearer " + response.token
//        ServiceBuilder.id = response.id
//        val repo=BookmarkRepository()
//        val actualResult=repo.BookamarkNote(Bookmark(userId = ServiceBuilder.id!!,noteId = "604e2a5a85f36912e44bddc8")).success
//        Assert.assertEquals(expectedResult, actualResult)
//    }
}