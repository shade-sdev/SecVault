using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace SecVault.MVVM.Model.Password;

[Table("password")]
public class Password
{
    [Key]
    [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
    [Column("id")]
    public int Id { get; set; }

    public PasswordInfo PasswordInfo { get; set; }

    public PasswordTypeInfo PasswordTypeInfo { get; set; }

    public CardInfo CardInfo { get; set; }

    public PasswordDate PasswordDate { get; set; }
}