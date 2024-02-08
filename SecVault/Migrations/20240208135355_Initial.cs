using System;
using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace SecVault.Migrations
{
    /// <inheritdoc />
    public partial class Initial : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "password",
                columns: table => new
                {
                    id = table.Column<int>(type: "INTEGER", nullable: false)
                        .Annotation("Sqlite:Autoincrement", true),
                    card_expiry_date = table.Column<DateTime>(type: "TEXT", nullable: true),
                    card_notes = table.Column<string>(type: "TEXT", nullable: true),
                    card_number = table.Column<string>(type: "TEXT", nullable: true),
                    card_pin = table.Column<string>(type: "TEXT", nullable: true),
                    creation_date = table.Column<DateTime>(type: "TEXT", nullable: true),
                    last_updated_date = table.Column<DateTime>(type: "TEXT", nullable: true),
                    email = table.Column<string>(type: "TEXT", nullable: true),
                    encrypted_password = table.Column<string>(type: "TEXT", nullable: true),
                    favourite = table.Column<bool>(type: "INTEGER", nullable: false),
                    user_name = table.Column<string>(type: "TEXT", nullable: true),
                    icon_url = table.Column<string>(type: "TEXT", nullable: true),
                    name = table.Column<string>(type: "TEXT", nullable: false),
                    password_type = table.Column<int>(type: "INTEGER", nullable: false),
                    website_url = table.Column<string>(type: "TEXT", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_password", x => x.id);
                });
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "password");
        }
    }
}
