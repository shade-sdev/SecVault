package repository.queries.user

object UserQueries {
   const val USER_EXIST_QUERY = "SELECT EXISTS(SELECT 1 FROM USERS)"
}