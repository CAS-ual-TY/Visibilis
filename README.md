

# Visibilis
**LAT**: visibilis, e
**ENG**: visible, capable of being seen

## About
Visibilis is a modification for Minecraft which contains basic functionality for in-game visual coding. It comes with a base structure of nodes, data types and an UI and allows for easy expansion.
The mod itself does not contain any gameplay elements or additions. It is only effectively usable by extending it with gameplay elements and allowing the player to use visual coding interface.

### Why
Personally, I have tried several attempts of a modification which allows the player to create and customize spells while in-game. Even thought these have all worked (with the latest instance - [Mundus Magicus](https://www.curseforge.com/minecraft/mc-mods/mundus-magicus) - working very great in my opinion) I have never been happy with the design choices behind any of these.
The spells in these mods had to be created out of components put together in a specific structure. These structures of the various mods always allowed full customization and endless possibilities in their own ways, but also minor flaws in a way that would force you to sometimes put components in twice if you wanted to achieve a certain functionality.
So I realized that the problem I had was not the way I setup these spell structures, but rather a structure itself.

### Visual Coding
So following up on the point above, I had several ideas of how to make a "no-structure" based spell mod. But all of these involved nodes and connections in one way or another. So I thought that I could also just make an entire visual coding functionality, as that would not be much more.
Additionally, while drafting some ideas, I took part in a "Nerd Memes" thread and someone said "make your own programming language for fun" as a meme which is kinda what I am doing (?).

### Example Mod Ideas
Here are some basic mod ideas I have. I don't know if I am going make any of these (except for Magica Mundi) but these ideas are there so why not.

#### Magica Mundi - *The magic things of this world*
The sequel to **[Mundus Magicus](https://www.curseforge.com/minecraft/mc-mods/mundus-magicus)**: Visual coding to design, create and customize your own spells (In case you are wondering: Mundus Magicus - *Magic world*).
A spell is made up of 3 basic components:

 - **Events - When** to "fire" the spell (eg. right click, every second, when taking damage etc.)
 - **Detectors - Who** or what to affect (eg. everyone within 5m of you, all pigs, your cursor etc.)
 - **Effects - What** to do or how to affect the targets (eg. lighting, healing or damage, freezing etc.)

You can customize all of these components using the visual coding interface. Say you want to damage everyone for 10% of their current health, then kill anyone below 5% of their max health and spawn a lighting at every entity killed that way: You can do that.
You can also just spam fart noices at every player every second. You can do that... too.

Now this is all the major stuff that needs to be known about this mod. The possibilities of this will be (/are?) endless. There are obviously more minor components and functionalities to this, but I will make a separate repository for this mod whenever I feel like these 2 mods are ready for public release.

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
 - Code contributions to Magica Mundi
 - Your own ideas and mods

Now, I have never worked with strangers on a project. And I never have created documentation for anyone else but myself. But **I try to structure and document the code as well and as much as possible**. Keeping this in mind, feel free to correct my wording and terminology wherever necessary. I have not learned it but I am willing to do so.
If you ever think that I made a poor design choice and **you have a good idea: Tell me about it**. I am not butthurt if anyone comes up with a better idea, quite the contrary!
Though in case you do think about making a pull-request: Please **contact me first** (see below)! I don't want anyone to put work into improvements with myself not agreeing to them.

### How to contribute

 - Pull requests
 - Contact me
 - Publish your own ideas and mods

### What to get started with
Terminology:

 - **Print**: Contains nodes which can be connected in various ways to create **functionality**
 - **Node**: A component which uses its inputs to **calculate** its outputs; All parent nodes must be calculated beforehand
 - **Node Field**: A connection anchor you could say: Either an **input** or an **output**. Outputs can be connected to various inputs, but inputs can each only be connected to a single output; They all have a specific data type
 - **"exec"**: The **execution chain** or **data type**; Usually connections transfer data for connected nodes to be used, but the "exec" type insead just decides **which node to calculate next**; **"exec"** **outputs** can all only be connected **to a single input**
 - **Exec Node**: A type of node which **carries** node fields of the **"exec"** data type; Nodes carry this data type if their execution order makes a difference
 - **Event Node**: Extends "Exec Node"; This is the **start of the execution chain** in each print depending on the **event type**

Code to take a look at:

 - **de.cas_ual_ty.visibilis.GuiPrint**: contains **basic user interface** functionality
 - **de.cas_ual_ty.visibilis.Print#execute(NodeExec)**: starts the node chain; "exec" nodes are calculated in succession
 - **de.cas_ual_ty.visibilis.node.Node#preCalculate()**: whenever a node is executed that has an uncalculated **"non-exec" parent**, that parent is **calculated first**, then the "exec" node

## Contact
I do not tend to be very active on GitHub itself with the exception of pushing commits. I would probably not see a message here for weeks. So:

### Discord: CAS_ual_TY#4737

## Credits
- **The Minecraft Team**: For being and creating something awesome
- **The Forge Team**: For being and creating something awesome, too
- **Wicca**: ["*make your own programming language*"](https://prteamwork.com/threads/nerd-memes.33581/#post-301451)
- **gendeathrow**: [For telling me about **glScissor**](https://www.minecraftforge.net/forum/topic/74794-render-gui-inside-rectangle/?tab=comments#comment-358643)
