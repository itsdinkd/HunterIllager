package baguchan.hunterillager.structure;


import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGenerator;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;

public class HunterHouseStructure extends StructureFeature<NoneFeatureConfiguration> {
	public HunterHouseStructure(Codec<NoneFeatureConfiguration> p_i51440_1_) {
		super(p_i51440_1_, PieceGeneratorSupplier.simple(PieceGeneratorSupplier.checkForBiomeOnTop(Heightmap.Types.WORLD_SURFACE_WG), HunterHouseStructure::generatePieces));
	}

	private static void generatePieces(StructurePiecesBuilder p_197233_, PieceGenerator.Context<NoneFeatureConfiguration> p_197234_) {
		BlockPos blockpos = new BlockPos(p_197234_.chunkPos().getMinBlockX(), 90, p_197234_.chunkPos().getMinBlockZ());
		Rotation rotation = Rotation.getRandom(p_197234_.random());
		HunterHousePieces.addStructure(p_197234_.structureManager(), blockpos, rotation, p_197233_, p_197234_.random());
	}

	@Override
	public GenerationStep.Decoration step() {
		return GenerationStep.Decoration.SURFACE_STRUCTURES;
	}
}
