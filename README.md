Operations Types:
This is a four-player game. For every player, she can do eight types of operations:
1.discard
2.chi
3.peng
4.hu
5.skip
6.fetch
7.check if chi is formed
8.check if peng is formed
9.check if hu is formed.
Among these operations, operation 6-9 are finished automatically. That is these four operations
will be done by the game itself without the interference of players. There is a tile in the center of the game board. 
This tile is a special tile which can be regarded as any tiles you want.

The reason for setting operations 7-9:
In the real mahjong game, when player1 discards a tile, all players whose tiles have existed a peng or hu form
will have the authority to make peng or hu operation. So such a game rule made the turn info very confused because we can not make
sure who will get next turn. To solve this problem, I set these three check operations(7-9). When a player discards a tile, the 
player next her will check if she can peng or hu automatically. If she can, she will get the next turn again to handle peng or
hu operation. If she can not, the turn will be given to her next player to do the same check. So for real player, they do not need
to consider the check process. Instead they just need to click chi, peng, hu or skip buttons when the check process has finished.
In addition, because of these three operations, when you play the game in the emulator, you will find once a player discarded a tile,
the turn will flash to the next player quickly. This is a normal phenomenon because the game is doing chi, peng, hu check after discard
operation automatically. This turn flash phenomenon will disappear when you deploy the game to the real container.



