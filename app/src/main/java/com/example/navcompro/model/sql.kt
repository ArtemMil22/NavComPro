package com.example.navcompro.model

/*
PRAGMA foreign_keys = ON;

CREATE TABLE "accounts" (
"id" 			INTEGER PRIMARY KEY,
"email" 		TEXT NOT NULL UNIQUE COLLATE NOCASE,
"username" 		TEXT NOT NULL,
"password" 		TEXT NOT NULL,
"created_at" 	INTEGER NOT NULL
);

CREATE TABLE "boxes" (
"id" 			INTEGER PRIMARY KEY,
"color_name" 	TEXT NOT NULL,
"color_value" 	TEXT NOT NULL
)

CREATE TABLE "accounts_boxes_settings" (
"account_id" INTEGER NOT NULL,
"box_id" INTEGER NOT NULL,
"is_active" INTEGER NOT NULL,
FOREIGN KEY("account_id") REFERENCES "accounts"("id"),
FOREIGN KEY("box_id") REFERENCES "boxes"("id"),
UNIQUE ("account_id","box_id")
)

INSERT INTO "accounts" ("email", "username",
"password", "created_at")
VALUES
("admin@google.com", "admin", "123", 0),
("tester@google.com", "tester", "321", 0);

INSERT INTO "boxes" ("color_name", "color_value")
VALUES
   ("Red", "#880000"),
   ("Green", "#008800"),
   ("Blue", "#000088"),
   ("Yellow", "#888800"),
   ("Violet", "#8800FF"),
   ("Black", "#000000");

   INSERT INTO "accounts_boxes_settings" (
"account_id", "box_id", "is_active"
)
VALUES
    (1, 1, 1),
	(1, 2, 0),
	(1, 3, 1),
	(1, 4, 0),
	(2, 1, 0);

	SELECT "id", "password"
FROM "accounts"
WHERE "email" = ?

UPDATE "accounts"
SET "username" = "admin123"
WHERE "id" = 1

SELECT "boxes".*, "accounts_boxes_settings"."is_active"
FROM "boxes"
LEFT JOIN "accounts_boxes_settings"
  ON "boxes"."id" = "accounts_boxes_settings"."box_id"
  AND "accounts_boxes_settings"."account_id" = 1
 */