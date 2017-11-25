package org.ausimus.wurmunlimited.mods.gambling.items;

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

import com.wurmonline.server.MiscConstants;
import com.wurmonline.server.TimeConstants;
import com.wurmonline.server.items.ItemTypes;
import com.wurmonline.shared.constants.IconConstants;
import com.wurmonline.shared.constants.ItemMaterials;
import org.ausimus.wurmunlimited.mods.gambling.Initiator;
import org.ausimus.wurmunlimited.mods.gambling.config.AusConstants;

import java.io.IOException;

public class GItemTemplateCreator
{
    public GItemTemplateCreator()
    {
        try
        {
            // Token
            com.wurmonline.server.items.ItemTemplateCreator.createItemTemplate(
                    AusConstants.GamblingTokenTemplateID,
                    "Gambling Token",
                    "Gambling Tokens",
                    "fresh",
                    "dry",
                    "brittle",
                    "rotten",
                    "A token.",
                    new short[]
                            {
                                    ItemTypes.ITEM_TYPE_HASDATA
                            },
                    (short) IconConstants.IRON_RUNE_JACKAL,
                    (short) 1,
                    0,
                    TimeConstants.DECAYTIME_NEVER,
                    1,
                    1,
                    1,
                    0,
                    MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY,
                    "LeNull",
                    100.0F,
                    1,
                    ItemMaterials.MATERIAL_GOLD,
                    0,
                    true);

            com.wurmonline.server.items.ItemTemplateCreator.createItemTemplate(
                    AusConstants.GamblingMachineTemplateID,
                    "Gambling Machine",
                    "Gambling Machines",
                    "fresh",
                    "dry",
                    "brittle",
                    "rotten",
                    "A Machine for gambling.",
                    new short[]
                            {
                                    ItemTypes.ITEM_TYPE_HOLLOW,
                                    ItemTypes.ITEM_TYPE_HASDATA,
                                    ItemTypes.ITEM_TYPE_NOTAKE,
                                    ItemTypes.ITEM_TYPE_DECORATION,
                                    //ItemTypes.ITEM_TYPE_NOPUT,
                                    ItemTypes.ITEM_TYPE_USE_GROUND_ONLY
                            },
                    (short) IconConstants.IRON_RUNE_JACKAL,
                    (short) 1,
                    0,
                    TimeConstants.DECAYTIME_NEVER,
                    500,
                    500,
                    500,
                    0,
                    MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY,
                    "model.ausimus.GamblingMachine.",
                    100.0F,
                    1,
                    ItemMaterials.MATERIAL_BRONZE,
                    0,
                    true);
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
            if (AusConstants.InDepthLogging)
            {
                Initiator.WriteLog(String.valueOf(ex));
            }
        }
    }
}