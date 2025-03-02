package repository.user.queries

object UserQueries {
    const val USER_EXIST_QUERY =
        """
        SELECT EXISTS(SELECT 1 FROM USERS)
        """

    const val USER_EXIST_DATA_QUERY =
        """
        SELECT EXISTS(SELECT 1 FROM passwords WHERE user_id = ?) 
        AND EXISTS(SELECT 1 FROM credit_cards WHERE user_id = ?)
        """
}