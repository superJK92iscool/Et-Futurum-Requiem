package ganymedes01.etfuturum;

import baubles.api.expanded.BaubleExpandedSlots;

public class CompatBaublesExpanded {

    public static int[] wingSlotIDs;

    public static void preInit() {
        BaubleExpandedSlots.tryAssignSlotsUpToMinimum(BaubleExpandedSlots.wingsType, 1);
    }

    public static void postInit() {
        wingSlotIDs = BaubleExpandedSlots.getIndexesOfAssignedSlotsOfType(BaubleExpandedSlots.wingsType);
    }
}
