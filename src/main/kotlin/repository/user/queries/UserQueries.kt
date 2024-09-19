package repository.user.queries

object UserQueries {
   const val USER_EXIST_QUERY = "SELECT EXISTS(SELECT 1 FROM USERS)"
}