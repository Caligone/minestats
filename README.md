Minecraft Stats
===============

A plugin for minecraft bukkit servers.  
The purpose of this plugin is to save statistics on players, so it can be used by a website.

## Requirements ##

This plugin was tested on a bukkit 1.7.2. server.  

## Installation ##

  * Clone this repo
  * Build the jar using maven : ** mvn package **
  * Place the jar (located in the target directory) in the **plugins** directory of the bukkit server directory

## Features ##

This plugin save infomation about players such as:
  * the number of placed blocks
  * the number of broken blocks
  * the number of deaths (with a distinction of normal deaths when killed by a npc or a player, stupid deaths in case of accidents)
  * the time played
  * the verbosity : the number of messages wrote in chat

## Commands ##

Displays the current player statistics
  * /stats
  
Displays a player statistics
  * /stats playerName

