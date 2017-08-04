package org.ausimus.wurmunlimited.mods.gambling;
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
import org.ausimus.wurmunlimited.mods.gambling.actions.ActionsInit;
import org.ausimus.wurmunlimited.mods.gambling.items.GItemTemplateCreator;
import org.gotti.wurmunlimited.modloader.interfaces.*;
import org.gotti.wurmunlimited.modsupport.actions.ModActions;

import java.util.Properties;

public class Initiator implements WurmServerMod, Configurable, PreInitable, ServerStartedListener, ServerPollListener, ItemTemplatesCreatedListener {

    /**
     * @param properties Will be some properties here later on.
     */
    @Override
    public void configure(Properties properties) {
    }

    @Override
    public void init() {
    }

    @Override
    public void preInit() {
        ModActions.init();
    }


    @Override
    public void onServerPoll() {
    }

    @Override
    public void onServerStarted() {
        new ActionsInit();
    }

    @Override
    public void onItemTemplatesCreated() {
        new GItemTemplateCreator();
    }
}