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

import com.wurmonline.server.*;
import com.wurmonline.server.economy.Change;
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

public class DebugToken implements WurmServerMod, ItemTypes, MiscConstants, ModAction, BehaviourProvider, ActionPerformer
{
    private static short actionID;
    private static ActionEntry actionEntry;

    DebugToken()
    {
        actionID = (short) ModActions.getNextActionId();
        actionEntry = ActionEntry.createEntry(actionID, "Get Roulette Info", "Getting", new int[]{});
        ModActions.registerAction(actionEntry);
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public BehaviourProvider getBehaviourProvider()
    {
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public ActionPerformer getActionPerformer()
    {
        return this;
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public short getActionId()
    {
        return actionID;
    }

    /**
     * {@inheritDoc}
     *
     * @param performer performer representing the instantiation of Creature.
     * @param source    The Item source.
     * @param target    The Item target.
     * @return {@link Collections#singletonList(java.lang.Object) object will = {@link DebugToken#actionEntry} else is null.}.
     **/
    @Override
    public List<ActionEntry> getBehavioursFor(Creature performer, Item source, Item target)
    {
        if (source == target && source.getTemplateId() == AusConstants.GamblingTokenTemplateID)
        {
            return Collections.singletonList(actionEntry);
        }
        else
        {
            return null;
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param act       the act.
     * @param performer performer representing the instantiation of Creature.
     * @param source    The Item source.
     * @param target    The Item target.
     * @param action    Action ID number.
     * @param counter   Timer shit.
     * @return boolean.
     **/
    @Override
    public boolean action(Action act, Creature performer, Item source, Item target, short action, float counter)
    {
        if (source == target && source.getTemplateId() == AusConstants.GamblingTokenTemplateID)
        {
            if (target.getColor() == AusConstants.Black)
            {
                performer.getCommunicator().sendNormalServerMessage("Color is Black.");
            }
            if (target.getColor() == AusConstants.White)
            {
                performer.getCommunicator().sendNormalServerMessage("Color is White.");
            }
            if (target.getData1() >= 0)
            {
                Change c = new Change(target.getData1());
                performer.getCommunicator().sendNormalServerMessage("Value is " + c.getChangeString() + ".");
            }
            if (target.getData2() >= 0)
            {
                performer.getCommunicator().sendNormalServerMessage("Number is " + target.getData2() + ".");
            }

        }
        else
        {
            performer.getCommunicator().sendNormalServerMessage("Cant do that.");
        }
        return true;
    }
}