


# Visibilis
**LAT**: visibilis, e
**ENG**: visible, capable of being seen

![Ingame screenshot](https://i.imgur.com/BIgukpx.png)
Current progress (28/01/2020)

### Current MC Version: 1.15.2

#### Curseforge Project Page
[https://www.curseforge.com/minecraft/mc-mods/visibilis](https://www.curseforge.com/minecraft/mc-mods/visibilis)

## About
Visibilis is a **modification for Minecraft** which contains basic functionality for **in-game visual coding**. It comes with a base structure of nodes, data types and an UI and allows for easy expansion.
The mod itself does not contain any gameplay elements or additions. It is only effectively usable by extending it with gameplay elements and allowing the player to use the visual coding interface.

### Why...
Personally, I have tried several attempts of a modification which allows the player to create and customize spells while in-game. Even thought these have all worked (with the latest instance - [Mundus Magicus](https://www.curseforge.com/minecraft/mc-mods/mundus-magicus) - working very great in my opinion) I have never been happy with the design choices behind any of these.
The spells in these mods had to be created out of components put together in a specific structure. These structures of the various mods always allowed full customization and endless possibilities in their own ways, but also minor flaws in a way that would force you to sometimes put components in twice if you wanted to achieve a certain functionality.
So I realized that the problem I had was not the way I setup these spell structures, but rather a structure itself.

### ... Visual Coding?
So following up on the point above, I had several ideas of how to make a "no-structure" based spell mod. But all of these involved nodes and connections in one way or another. So I thought that I could also just make an entire visual coding functionality, as that would not be much more.
Additionally, while drafting some ideas, I took part in a "Nerd Memes" thread and someone said "make your own programming language for fun" as a meme which is kinda what I am doing (?).

### Example Mod Ideas
Here are some basic mod ideas I have. I don't know if I am going make any of these (except for Magica Mundi) but these ideas are there so why not.

#### Magica Mundi - *The magic things of this world*
The sequel to **[Mundus Magicus](https://www.curseforge.com/minecraft/mc-mods/mundus-magicus)**: Visual coding to design, create and customize your own spells (In case you are wondering: Mundus Magicus - *Magic world*).
Also open source on GitHub: **[Magica Mundi](https://github.com/CAS-ual-TY/MagicaMundi)**.

#### Programmable Machines - An idea
Programm your own machines. Kinda like the known Mining Turtles from Computer Craft. But in a more appealing visual programming language instead. Or maybe your own War-Bots? Patrolling Knights?

#### Gamemodes - An idea
Add elements which can be interacted with in the visual coding interface, to allow the creation and customization of gamemodes. Eg. add some flags or bombs and allow the player to use these things in combination with the coding to create his own gamemodes, like: Search & Destroy (or CS:GO Competitive), Capture the Flag or Flag Conquest, etc..

#### Admin Tools - Another idea
Add basic elements which can be interacted with in the visual coding interface, to allow the creation and customization of administrative or world manipulation tools. This would be healing everyone in a certain area, restricting access of a part to certain players, quests or mining/reward areas, etc..

#### More ideas
Possibilities are endless. You will come up with something I am sure.

## Contribute
A list of contributors is at the bottom.

### What to contribute
 - Code contributions to Visibilis
 - Code contributions to **[Magica Mundi](https://github.com/CAS-ual-TY/MagicaMundi)**
 - Your own ideas and mods

Now, I have never worked with strangers on a project. And I never have created documentation for anyone else but myself. But **I try to structure and document the code as well and as much as possible**. Keeping this in mind, feel free to correct my wording and terminology wherever necessary. I have not learned it but I am willing to do so.
If you ever think that I made a poor design choice and **you have a good idea: Tell me about it**. I am not butthurt if anyone comes up with a better idea, quite the contrary!
Though in case you do think about making a pull-request: Please **contact me first** (see below)! I don't want anyone to put work into improvements with myself not agreeing to them.

### How to contribute
 - Pull requests
 - Contact me
 - Publish your own ideas and mods

#### Setup Visibilis as Mod API (eclipse)
How to setup eclipse so that you can work with this API (all files about to be mentioned can be found here, in this repo in build/libs):
- Create a new forge gradle project and do a normal eclipse setup
- Download the deobf jar and put it into your xxx/run/mods folder (create if non-existent)
- Download the source jar of the same version as the deobf jar and put it into your xxx/lib folder (create if non-existent)
- Include the deobf jar into your java build path of your eclipse project (Project > Properties > Java Build Path > Libraries > Add external JARs...)
- In your Package Explorer, unbox Referenced Libraries, find the deobf jar and attach the source to it (Right Click > Properties > Java Source Attachment > External Location > External File...)

Instead of using only releases, you can also build everything based on this repo which should almost always be ahead of the latest release. Gradlew commands:
- gradlew build - To build the mod jar (obviously)
- gradlew sourcesJar - To build the sources jar
Both files will be in build/libs

#### Setup Visibilis for contributions
If you want to work on Visibilis itself, simply fork or clone this repo and then run the normal gradle commands for your IDE (eg. forge eclipse & forge genEclipseRuns).

### What to get started with
If you are looking for a guide on how to setup so you can work with/on Visibilis, just look right above this sentence. The next part is just a collection of things to understand/take a look at to understand the concepts and terminology.

#### Terminology
 - **Print**: Contains nodes which can be connected in various ways to create **functionality**
 - **Node**: A component which uses its inputs to **calculate** its outputs; All parent nodes must be calculated beforehand
 - **Node Field**: A connection anchor you could say: Either an **input** or an **output**. Outputs can be connected to various inputs, but inputs can each only be connected to a single output; They all have a specific data type
 - **"exec"**: The **execution chain** or **data type**; Usually connections transfer data for connected nodes to be used, but the "exec" type insead just decides **which node to calculate next**; **"exec"** **outputs** can all only be connected **to a single input**
 - **Exec Node**: A type of node which **carries** node fields of the **"exec"** data type; Nodes carry this data type if their execution order makes a difference
 - **Event Node**: A special "Exec Node"; This is one possible **start of the execution chain** in each print depending on the **event type**

#### Code to take a look at
 - **de.cas_ual_ty.visibilis.print.ui.UiBase**: Contains **all required user interface** functionality; you can open this GUI via **de.cas_ual_ty.visibilis.util.VUtility**
 - **de.cas_ual_ty.visibilis.print.PrintProvider**: Extend this to have callbacks to the UI; this is **all thats needed** to implement and use the full UI; also comes with base implementations eg. **de.cas_ual_ty.print.item.XXX**
 - **de.cas_ual_ty.visibilis.print.Print#execute(Node, DataProvider)**: Starts the node chain; "exec" nodes are calculated in succession
 - **de.cas_ual_ty.visibilis.node.dataprovider.DataProvider**: Allows you **to pass information** to your nodes that is required; eg. if your prints only fire for a custom entity and they need that instance, you pass it via this class
 - **de.cas_ual_ty.visibilis.node.Node#preCalculate(DataProvider)**: Whenever a node is executed that has an uncalculated **"non-exec" parent**, that parent is **calculated first**, then the "exec" node
 - **de.cas_ual_ty.visibilis.node.exec.XXX**: Good **examples** for exec nodes; for loop, if branch, while loop, etc.
 - **de.cas_ual_ty.visibilis.node.general.NodePrint**: Good example for a basic, independent node

#### Other examples
 - **[Magica Mundi GitHub Repo](https://github.com/CAS-ual-TY/MagicaMundi)** A mod created by myself built on this API; if you dont know how to use this check out the repo

## Contact
I do not tend to be very active on GitHub itself with the exception of pushing commits. I would probably not see a message here for weeks. So:

### Discord: CAS_ual_TY#4737

## Credits
- **The Minecraft Team**: For being and creating something awesome
- **The Forge Team**: For being and creating something awesome, too
- **Wicca**: ["*make your own programming language*"](https://prteamwork.com/threads/nerd-memes.33581/#post-301451)
- **gendeathrow**: [For telling me about **glScissor**](https://www.minecraftforge.net/forum/topic/74794-render-gui-inside-rectangle/?tab=comments#comment-358643)
