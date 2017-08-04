package org.ausimus.wurmunlimited.mods.gambling.actions.roulette;

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

import com.wurmonline.server.*;
import com.wurmonline.server.items.*;
import org.ausimus.wurmunlimited.mods.gambling.config.AusConstants;
import org.gotti.wurmunlimited.modloader.interfaces.WurmServerMod;
import org.gotti.wurmunlimited.modsupport.actions.ActionPerformer;
import org.gotti.wurmunlimited.modsupport.actions.BehaviourProvider;
import org.gotti.wurmunlimited.modsupport.actions.ModAction;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;
import com.wurmonline.server.behaviours.Action;
import com.wurmonline.server.behaviours.ActionEntry;
import com.wurmonline.server.creatures.Creature;

import java.util.Collections;
import java.util.List;

public class GetRouletteTokenInfo implements WurmServerMod, ItemTypes, MiscConstants, ModAction, BehaviourProvider, ActionPerformer {
    private static short actionID;
    private static ActionEntry actionEntry;

    public GetRouletteTokenInfo() {
        actionID = (short) ModActions.getNextActionId();
        actionEntry = ActionEntry.createEntry(actionID, "Get Bet Info", "Getting", new int[]{});
        ModActions.registerAction(actionEntry);
    }

    @Override
    public BehaviourProvider getBehaviourProvider() {
        return this;
    }

    @Override
    public ActionPerformer getActionPerformer() {
        return this;
    }

    @Override
    public short getActionId() {
        return actionID;
    }

    /**
     * @param performer performer representing the instantiation of Creature.
     * @param source    The Item source.
     * @param target    The Item target.
     * @return Fuck Warnings.
     **/
    @Override
    public List<ActionEntry> getBehavioursFor(Creature performer, Item source, Item target) {
        if (source == target
                && source.getTemplateId() == AusConstants.GamblingTokenTemplateID
                && target.getAuxData() == AusConstants.GameModeRoulette) {
            return Collections.singletonList(actionEntry);
        } else {
            return null;
        }
    }

    /**
     * @param act       the act.
     * @param performer performer representing the instantiation of Creature.
     * @param source    The Item source.
     * @param target    The Item target.
     * @param action    Action ID number.
     * @param counter   Timer shit.
     * @return Fuck Warnings.
     **/
    @Override
    public boolean action(Action act, Creature performer, Item source, Item target, short action, float counter) {
        if (source == target
                && source.getTemplateId() == AusConstants.GamblingTokenTemplateID
                && target.getAuxData() == AusConstants.GameModeRoulette) {
            if (target.getColor() == AusConstants.Black) {
                performer.getCommunicator().sendNormalServerMessage("Color is Black.");
            }
            if (target.getColor() == AusConstants.White) {
                performer.getCommunicator().sendNormalServerMessage("Color is White.");
            }
        } else {
            performer.getCommunicator().sendNormalServerMessage("Cant do that");
        }
        return true;
    }
}