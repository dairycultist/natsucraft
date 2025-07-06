# Dungeoncraft

I'm gonna cut down the scope of this project to just megalophobia-inducing above-ground dungeons (megaliths). I'm thinking
it could just be an 100% serverside mod with no added Things but with new structures.

I just wanna come across a strange cobblestone monument in Minecraft beta and explore/loot its kilometers of hallways :3

The thing with Minecraft structures is they're so drop-in-a-bucket, they should be incorporated into the surroundings more.
Like how biomes aren't just 1 chunk of biome, structures should be more spread out, with peripheral structures and bigger
central structures incorporated into the terrain. Ancient ruins that build on the lonely feeling in b1.7.3.
Stone structures that could possibly date back to some kind of really old lost civilization.

## Implemented (subject to change)

### Structures

- MegalithFeature

### Mobs

- Add Lich ([inspiration](https://the-grimoire-of-gaia.fandom.com/wiki/Bone_Knight)) ([skin base](https://namemc.com/skin/adcc6eab0088f51e))
  - end goal of the game is to defeat the lich (?)
  - After a certain amount of damage, the lich uses necromancy to summon three skeletons in a puff of smoke to assist it in battle
  - summoned by...
  - drops the lich sword
- Add Mummy ([skin base](https://www.minecraftskins.com/skin/22900078/mummy/))
  - Drops fabric
  - Basically just a retextured zombie that doesn't burn in daylight
- Chicken
  - skin variants (I like the Deep Aether's pastel quails)
  - they have tails now
  - drops meat
- Spiders are no longer slowed by cobwebs (can this work serverside only? idk)

## To-do

- remove Lich, Mummy, and Chicken (save some of the code tho)
- Refactor to `dairycultist.<something>`
- Some sort of mechanic that encourages both 1) building a permanent, large base and 2) often leaving base to explore far away before returning
- naming conventions in beta are convenient because when I add slightly brighter stone with a border and cracked slightly brighter stone with a border and slightly brighter stone with a border subdivided into four quadrants I can just call all of them "stone"
- chest with iron trim