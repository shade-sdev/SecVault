using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using SecVault.MVVM.Model.PasswordCategory;

namespace SecVault.MVVM.Model.Password;

[ComplexType]
public class PasswordTypeInfo
{
    [Required] 
    [Column("name")] 
    public string? Name { get; set; }

    [Url] 
    [Column("website_url")] 
    public string? WebsiteSiteUrl { get; set; }

    [Url] 
    [Column("icon_url")] 
    public string? IconUrl { get; set; }

    [Column("password_type")]
    public PasswordCategoryType PasswordCategoryType { get; set; } = PasswordCategoryType.Password;
}