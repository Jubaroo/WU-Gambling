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

import com.wurmonline.server.Items;
import com.wurmonline.server.economy.Change;
import com.wurmonline.server.items.*;
import org.ausimus.wurmunlimited.mods.gambling.Initiator;
import org.ausimus.wurmunlimited.mods.gambling.config.AusConstants;
import org.gotti.wurmunlimited.modloader.interfaces.WurmServerMod;
import org.gotti.wurmunlimited.modsupport.actions.ActionPerformer;
import org.gotti.wurmunlimited.modsupport.actions.BehaviourProvider;
import org.gotti.wurmunlimited.modsupport.actions.ModAction;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;
import com.wurmonline.server.MiscConstants;
import com.wurmonline.server.behaviours.Action;
import com.wurmonline.server.behaviours.ActionEntry;
import com.wurmonline.server.creatures.Creature;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class TokenRedeem implements WurmServerMod, ItemTypes, MiscConstants, ModAction, BehaviourProvider, ActionPerformer
{
    private static short actionID;
    private static ActionEntry actionEntry;

    TokenRedeem()
    {
        actionID = (short) ModActions.getNextActionId();
        actionEntry = ActionEntry.createEntry(actionID, "Redeem Token", "Redeeming", new int[]{});
        ModActions.registerAction(actionEntry);
    }

    @Override
    public BehaviourProvider getBehaviourProvider()
    {
        return this;
    }

    @Override
    public ActionPerformer getActionPerformer()
    {
        return this;
    }

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
     * @return {@link Collections#singletonList(java.lang.Object) object will = {@link TokenRedeem#actionEntry} else is null.}.
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
     * @param act
     * @param performer
     * @param source
     * @param target
     * @param action
     * @param counter
     * @return
     */
    @Override
    public boolean action(Action act, Creature performer, Item source, Item target, short action, float counter)
    {
        if (source == target && source.getTemplateId() == AusConstants.GamblingTokenTemplateID)
        {
            if (source.getData1() < 1)
            {
                performer.getCommunicator().sendNormalServerMessage("The token has no value.");
                return true;
            }
            Redeem(performer, target);
        }
        return true;
    }

    /**
     * {@inheritDoc}
     *
     * @param performer The performer.
     * @param target    The target.
     */
    private void Redeem(Creature performer, Item target)
    {
        try
        {
            Change c = new Change(performer.getMoney() + target.getData1());
            performer.setMoney(performer.getMoney() + target.getData1());
            performer.getCommunicator().sendNormalServerMessage("New Bank balance: " + c.getChangeString() + ".");
            Items.destroyItem(target.getWurmId());
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
            Initiator.WriteLog(String.valueOf(ex));
        }
    }
}