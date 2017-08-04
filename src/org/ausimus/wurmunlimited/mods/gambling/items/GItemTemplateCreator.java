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
import org.ausimus.wurmunlimited.mods.gambling.config.AusConstants;

import java.io.IOException;

public class GItemTemplateCreator {
    public GItemTemplateCreator() {
        try {
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
                    /*Behaviour Type*/(short) 1,
                    0,
                    TimeConstants.DECAYTIME_NEVER,
                    3,
                    4,
                    150,
                    0,
                    MiscConstants.EMPTY_BYTE_PRIMITIVE_ARRAY,
                    "null",
                    100.0F,
                    1,
                    (byte) 9,
                    0,
                    true);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}