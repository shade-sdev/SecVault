using System.Collections.ObjectModel;
using SecVault.MVVM.Model.Password;
using SecVault.MVVM.Model.PasswordCategory;
using SecVault.MVVM.Model.PasswordSort;

namespace SecVault.Core;

public static class ConstantService
{
    public static List<PasswordCategory> GetPasswordCategories()
    {
        return
        [
            new PasswordCategory()
            {
                Name = "Passwords",
                Icon = "/SecVault;component/Icons/Shield.png",
                IconSelected = "/SecVault;component/Icons/ShieldFilled.png",
                PasswordCategoryType = PasswordCategoryType.Password
            },

            new PasswordCategory()
            {
                Name = "Cards",
                Icon = "/SecVault;component/Icons/Card.png",
                IconSelected = "/SecVault;component/Icons/CardFilled.png",
                PasswordCategoryType = PasswordCategoryType.Card
            },

            new PasswordCategory()
            {
                Name = "Notes",
                Icon = "/SecVault;component/Icons/Note.png",
                IconSelected = "/SecVault;component/Icons/NoteFilled.png",
                PasswordCategoryType = PasswordCategoryType.Note
            }
        ];
    }

    public static List<PasswordSort> GetPasswordSorts()
    {
        return
        [
            new PasswordSort()
            {
                Name = "Name",
                CategoryType = PasswordSortType.Name
            },
            new PasswordSort()
            {
                Name = "Username",
                CategoryType = PasswordSortType.Username
            },
            new PasswordSort()
            {
                Name = "Email",
                CategoryType = PasswordSortType.Email
            },
            new PasswordSort()
            {
                Name = "Favourite",
                CategoryType = PasswordSortType.Favourite
            },
            new PasswordSort()
            {
                Name = "Created",
                CategoryType = PasswordSortType.CreatedDate
            },
            new PasswordSort()
            {
                Name = "Updated",
                CategoryType = PasswordSortType.LastUpdatedDate
            },
        ];
    }

    public static ObservableCollection<Password> GetPasswords()
    {
        return new ObservableCollection<Password>(
            [
                new Password()
                {
                    PasswordInfo = new PasswordInfo
                    {
                        Username = "Shade",
                        Email = "Shade@Shade.ga",
                        EncPassword = "Password",
                        IsFavourite = true
                    },
                    PasswordTypeInfo = new PasswordTypeInfo
                    {
                        Name = "Facebook",
                        WebsiteSiteUrl = "https://facebook.com/favicon.ico",
                        IconUrl = "https://facebook.com/favicon.ico",
                        PasswordCategoryType = PasswordCategoryType.Password
                    },
                    PasswordDate = new PasswordDate
                    {
                        CreatedDate = DateTime.Now,
                        LastUpdatedDate = DateTime.Now
                    }
                },
                new Password()
                {
                    PasswordInfo = new PasswordInfo
                    {
                        Username = "Shade",
                        Email = "Shade@Shade.ga",
                        EncPassword = "Password",
                        IsFavourite = false
                    },
                    PasswordTypeInfo = new PasswordTypeInfo
                    {
                        Name = "Facebook",
                        WebsiteSiteUrl = "https://facebook.com/favicon.ico",
                        IconUrl = "https://facebook.com/favicon.ico",
                        PasswordCategoryType = PasswordCategoryType.Password
                    },
                    PasswordDate = new PasswordDate
                    {
                        CreatedDate = DateTime.Now,
                        LastUpdatedDate = DateTime.Now
                    }
                },
                new Password()
                {
                    PasswordInfo = new PasswordInfo
                    {
                        Username = "Shade",
                        Email = "Shade@Shade.ga",
                        EncPassword = "Password",
                        IsFavourite = true
                    },
                    PasswordTypeInfo = new PasswordTypeInfo
                    {
                        Name = "Facebook",
                        WebsiteSiteUrl = "https://facebook.com/favicon.ico",
                        IconUrl = "https://facebook.com/favicon.ico",
                        PasswordCategoryType = PasswordCategoryType.Password
                    },
                    PasswordDate = new PasswordDate
                    {
                        CreatedDate = DateTime.Now,
                        LastUpdatedDate = DateTime.Now
                    }
                },
                new Password()
                {
                    PasswordInfo = new PasswordInfo
                    {
                        Username = "Shade",
                        Email = "Shade@Shade.ga",
                        EncPassword = "Password",
                        IsFavourite = true
                    },
                    PasswordTypeInfo = new PasswordTypeInfo
                    {
                        Name = "Facebook",
                        WebsiteSiteUrl = "https://facebook.com/favicon.ico",
                        IconUrl = "https://facebook.com/favicon.ico",
                        PasswordCategoryType = PasswordCategoryType.Password
                    },
                    PasswordDate = new PasswordDate
                    {
                        CreatedDate = DateTime.Now,
                        LastUpdatedDate = DateTime.Now
                    }
                },
                new Password()
                {
                    PasswordInfo = new PasswordInfo
                    {
                        Username = "Shade",
                        Email = "Shade@Shade.ga",
                        EncPassword = "Password",
                        IsFavourite = true
                    },
                    PasswordTypeInfo = new PasswordTypeInfo
                    {
                        Name = "Facebook",
                        WebsiteSiteUrl = "https://facebook.com/favicon.ico",
                        IconUrl = "https://facebook.com/favicon.ico",
                        PasswordCategoryType = PasswordCategoryType.Password
                    },
                    PasswordDate = new PasswordDate
                    {
                        CreatedDate = DateTime.Now,
                        LastUpdatedDate = DateTime.Now
                    }
                },
                new Password()
                {
                    PasswordInfo = new PasswordInfo
                    {
                        Username = "Shade",
                        Email = "Shade@Shade.ga",
                        EncPassword = "Password",
                        IsFavourite = true
                    },
                    PasswordTypeInfo = new PasswordTypeInfo
                    {
                        Name = "Facebook",
                        WebsiteSiteUrl = "https://facebook.com/favicon.ico",
                        IconUrl = "https://facebook.com/favicon.ico",
                        PasswordCategoryType = PasswordCategoryType.Password
                    },
                    PasswordDate = new PasswordDate
                    {
                        CreatedDate = DateTime.Now,
                        LastUpdatedDate = DateTime.Now
                    }
                },
                new Password()
                {
                    PasswordInfo = new PasswordInfo
                    {
                        Username = "Shade",
                        Email = "Shade@Shade.ga",
                        EncPassword = "Password",
                        IsFavourite = true
                    },
                    PasswordTypeInfo = new PasswordTypeInfo
                    {
                        Name = "Facebook",
                        WebsiteSiteUrl = "https://facebook.com/favicon.ico",
                        IconUrl = "https://facebook.com/favicon.ico",
                        PasswordCategoryType = PasswordCategoryType.Password
                    },
                    PasswordDate = new PasswordDate
                    {
                        CreatedDate = DateTime.Now,
                        LastUpdatedDate = DateTime.Now
                    }
                },
                new Password()
                {
                    PasswordInfo = new PasswordInfo
                    {
                        Username = "Shade",
                        Email = "Shade@Shade.ga",
                        EncPassword = "Password",
                        IsFavourite = true
                    },
                    PasswordTypeInfo = new PasswordTypeInfo
                    {
                        Name = "Facebook",
                        WebsiteSiteUrl = "https://facebook.com/favicon.ico",
                        IconUrl = "https://facebook.com/favicon.ico",
                        PasswordCategoryType = PasswordCategoryType.Password
                    },
                    PasswordDate = new PasswordDate
                    {
                        CreatedDate = DateTime.Now,
                        LastUpdatedDate = DateTime.Now
                    }
                },
                new Password()
                {
                    PasswordInfo = new PasswordInfo
                    {
                        Username = "Shade",
                        Email = "Shade@Shade.ga",
                        EncPassword = "Password",
                        IsFavourite = true
                    },
                    PasswordTypeInfo = new PasswordTypeInfo
                    {
                        Name = "Facebook",
                        WebsiteSiteUrl = "https://facebook.com/favicon.ico",
                        IconUrl = "https://facebook.com/favicon.ico",
                        PasswordCategoryType = PasswordCategoryType.Password
                    },
                    PasswordDate = new PasswordDate
                    {
                        CreatedDate = DateTime.Now,
                        LastUpdatedDate = DateTime.Now
                    }
                },
                new Password()
                {
                    PasswordInfo = new PasswordInfo
                    {
                        Username = "Shade",
                        Email = "Shade@Shade.ga",
                        EncPassword = "Password",
                        IsFavourite = true
                    },
                    PasswordTypeInfo = new PasswordTypeInfo
                    {
                        Name = "Facebook",
                        WebsiteSiteUrl = "https://facebook.com/favicon.ico",
                        IconUrl = "https://facebook.com/favicon.ico",
                        PasswordCategoryType = PasswordCategoryType.Password
                    },
                    PasswordDate = new PasswordDate
                    {
                        CreatedDate = DateTime.Now,
                        LastUpdatedDate = DateTime.Now
                    }
                },
                new Password()
                {
                    PasswordInfo = new PasswordInfo
                    {
                        Username = "Shade",
                        Email = "Shade@Shade.ga",
                        EncPassword = "Password",
                        IsFavourite = true
                    },
                    PasswordTypeInfo = new PasswordTypeInfo
                    {
                        Name = "Facebook",
                        WebsiteSiteUrl = "https://facebook.com/favicon.ico",
                        IconUrl = "https://facebook.com/favicon.ico",
                        PasswordCategoryType = PasswordCategoryType.Password
                    },
                    PasswordDate = new PasswordDate
                    {
                        CreatedDate = DateTime.Now,
                        LastUpdatedDate = DateTime.Now
                    }
                },
                new Password()
                {
                    PasswordInfo = new PasswordInfo
                    {
                        Username = "Shade",
                        Email = "Shade@Shade.ga",
                        EncPassword = "Password",
                        IsFavourite = true
                    },
                    PasswordTypeInfo = new PasswordTypeInfo
                    {
                        Name = "Facebook",
                        WebsiteSiteUrl = "https://facebook.com/favicon.ico",
                        IconUrl = "https://facebook.com/favicon.ico",
                        PasswordCategoryType = PasswordCategoryType.Password
                    },
                    PasswordDate = new PasswordDate
                    {
                        CreatedDate = DateTime.Now,
                        LastUpdatedDate = DateTime.Now
                    }
                }
            ]
        );
    }
}