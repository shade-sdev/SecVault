using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace SecVault.MVVM.Model.Password;

[ComplexType]
public class PasswordInfo
{
    [Column("user_name")] 
    public string? Username { get; set; }

    [Column("email")] 
    public string? Email { get; set; }

    [MaxLength]
    [Column("encrypted_password")]
    public string? EncPassword { get; set; }

    [Column("favourite")] 
    public bool IsFavourite { get; set; } = false;
}