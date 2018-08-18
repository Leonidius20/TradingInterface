# Trading Interface
**Trading Interface** is a shop system with graphical interface for [NukkitX][nukkitx] server software for Minecraft Bedrock Edition!

### Features:
* Buying and selling items & shop management through an intuitive interface;
* The list of items, available for buying, is divided into categories for convenience;
* Discounts for items;
* Custom names for items.

### Installation:
* Make sure that [EconomyAPI][economy] is installed on your server;
* Place plugin's .jar file into "plugins" folder on your server;
* Launch the server and set up the plugin through the interface.

### Setting up:
* Shop management functions are available for players with "shop.edit" permissions (operators have this permission by default).

### Configuration file:
* _language_ — plugin's language. If "default", server's language will be used. If there is no translation in your language, a file called <language_code>.lng with strings in English will be created in the plugin's folder. You can translate them on your own.
* _edit-logging_ — if "true", all actions with the lists of buyable and sellable items and the list of custom names for items will be logged.
* _transaction-logging_ — if "true", all purchases and sales will be logged.
* _save-translation_ — if "true", a file called <language_code>.lng with all strings in the plugin's current language will be created in the plugin's folder. If there is no translation to the current language, all strings will be in English. You can edit these strings.

### Usage:
* To open up shop's main menu type /shop into the chat.

### Building from sources
* In order to build the plugin from sources you must add [NukkitX][nukkitx] and [EconomyAPI][economy] as libraries.

### Links
* [Trading Interface on nukkitx.com](https://nukkitx.com/resources/trading-interface.113/)
* [Trading Interface on nukkit.ru](http://forums.voxelwind.com/resources/trading-interface.144/)

[economy]: https://github.com/EconomyS/EconomyAPI
[nukkitx]: http://github.com/NukkitX/Nukkit
