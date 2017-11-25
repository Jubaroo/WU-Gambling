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

import com.wurmonline.server.questions.AusBankWithDraw;
import org.ausimus.wurmunlimited.mods.gambling.actions.roulette.*;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

public class ActionsInit
{
    public ActionsInit()
    {
        ModActions.registerAction(new PurchaseToken());
        ModActions.registerAction(new RouletteSpin());
        ModActions.registerAction(new PickBlack());
        ModActions.registerAction(new PickWhite());
        ModActions.registerAction(new SetTargetRoulette());
        ModActions.registerAction(new PickNumber());
        ModActions.registerAction(new DebugToken());
        ModActions.registerAction(new TokenRedeem());
        ModActions.registerAction(new PlaceBet());
        ModActions.registerAction(new DebugMachine());
        ModActions.registerAction(new AusBankWithDraw());
    }
}