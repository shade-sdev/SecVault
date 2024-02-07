using SecVault.MVVM.Model.PasswordCategory;

namespace SecVault.MVVM.Model.Password;

public class PasswordTypeInfo
{
    public string? Name { get; set; }
        
    public string? WebsiteSiteUrl { get; set; }
    
    public string? IconUrl { get; set; }
    
    public PasswordCategoryType PasswordCategoryType { get; set; } = PasswordCategoryType.Password;
}