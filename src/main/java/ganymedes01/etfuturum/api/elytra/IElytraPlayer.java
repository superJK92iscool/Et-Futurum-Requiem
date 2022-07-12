package ganymedes01.etfuturum.api.elytra;

public interface IElytraPlayer {
    boolean etfu$isElytraFlying();
    void etfu$setElytraFlying(boolean flag);
    float etfu$getTicksElytraFlying();

    boolean etfu$lastElytraFlying();
    void etfu$setLastElytraFlying(boolean flag);
    void tickElytra();
}
