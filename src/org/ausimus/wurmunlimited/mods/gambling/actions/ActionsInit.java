package org.ausimus.wurmunlimited.mods.gambling.actions;

/*
     ___          ___          ___                     ___          ___          ___
    /\  \        /\__\        /\  \         ___       /\__\        /\__\        /\  \
   /::\  \      /:/  /       /::\  \       /\  \     /::|  |      /:/  /       /::\  \
  /:/\:\  \    /:/  /       /:/\ \  \      \:\  \   /:|:|  |     /:/  /       /:/\ \  \
 /::\~\:\  \  /:/  /  ___  _\:\~\ \  \     /::\__\ /:/|:|__|__  /:/  /  ___  _\:\~\ \  \
/:/\:\ \:\__\/:/__/  /\__\/\ \:\ \ \__\ __/:/\/__//:/ |::::\__\/:/__/  /\__\/\ \:\ \ \__\
\/__\:\/:/  /\:\  \ /:/  /\:\ \:\ \/__//\/:/  /   \/__/~~/:/  /\:\  \ /:/  /\:\ \:\ \/__/
     \::/  /  \:\  /:/  /  \:\ \:\__\  \::/__/          /:/  /  \:\  /:/  /  \:\ \:\__\
     /:/  /    \:\/:/  /    \:\/:/  /   \:\__\         /:/  /    \:\/:/  /    \:\/:/  /
    /:/  /      \::/  /      \::/  /     \/__/        /:/  /      \::/  /      \::/  /
    \/__/        \/__/        \/__/                   \/__/        \/__/        \/__/

*/

import org.ausimus.wurmunlimited.mods.gambling.actions.roulette.*;
import org.ausimus.wurmunlimited.mods.gambling.actions.slots.SetTokenSlots;
import org.ausimus.wurmunlimited.mods.gambling.actions.slots.SlotRoll;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

public class ActionsInit {
    public ActionsInit() {
        ModActions.registerAction(new PurchaseToken());
        ModActions.registerAction(new SlotRoll());
        ModActions.registerAction(new RouletteSpin());
        ModActions.registerAction(new PickBlack());
        ModActions.registerAction(new PickWhite());
        ModActions.registerAction(new SetTokenSlots());
        ModActions.registerAction(new SetTokenRoulette());
        ModActions.registerAction(new PickNumber());
        ModActions.registerAction(new GetRouletteTokenInfo());
    }
}
