using SecVault.MVVM.Model.PasswordCategory;

namespace SecVault.Core;

public static class PasswordCategoryProvider
{
    public static List<PasswordCategory> GetList()
    {
        return
        [
            new PasswordCategory()
            {
                Name = "Passwords",
                Icon = "/SecVault;component/Icons/Shield.png",
                IconSelected = "/SecVault;component/Icons/ShieldFilled.png",
                CategoryType = CategoryType.Password
            },

            new PasswordCategory()
            {
                Name = "Cards",
                Icon = "/SecVault;component/Icons/Card.png",
                IconSelected = "/SecVault;component/Icons/CardFilled.png",
                CategoryType = CategoryType.Card
            },

            new PasswordCategory()
            {
                Name = "Notes",
                Icon = "/SecVault;component/Icons/Note.png",
                IconSelected = "/SecVault;component/Icons/NoteFilled.png",
                CategoryType = CategoryType.Note
            }
        ];
    }
    
}