package repository.user

interface UserRepository {

    fun findAll(): List<User>

}