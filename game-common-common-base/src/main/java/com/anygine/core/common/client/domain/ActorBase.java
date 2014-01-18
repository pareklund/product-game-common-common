package com.anygine.core.common.client.domain;

import com.anygine.core.common.client.geometry.Vector2;

public abstract class ActorBase
  <S extends GameComponentState, L extends Level<?, ?>>
  extends GameComponentBase<S, L> 
  implements Actor<S, L> {

  protected int score;
	
	public ActorBase(
//	    long id, int version,
			String name, String type, Vector2 position, int width, int height, 
			Vector2 velocity, int points, L level, String spritePath) {
		super(
//		    id, version,
				name, type, position, width, height, velocity, points, 
				level, spritePath);
		score = 0;
	}

/*	
	protected ActorBase(ActorBase other, Json.Object updateSpec) {
	  super(other, updateSpec);
	  this.moveSpeed = other.moveSpeed;
	  this.fieldOfView = other.fieldOfView;
	  this.hearingRange = other.hearingRange;
	  this.score = other.score;
	  this.faceDirection = other.faceDirection;
	  this.inventory = other.inventory.entityHolderCopy(
	      updateSpec.getObject("inventory"));
	  this.killedSound = other.killedSound;
	}
*/

	@Override
	public int getScore() {
		return score;
	}
	
	@Override
	public void increaseScore(int points) {
		this.score += points;
	}
	
/*	
	@Override
	public void update(Json.Object jsonObj) {
	  super.update(jsonObj);
	  update(SoundWithPath.class, killedSound, jsonObj, "killedSound");
	  score = update(score, jsonObj, "score");
	  faceDirection = updateEnum(faceDirection, jsonObj, "faceDirection");
	  inventory = update(Inventory.class, inventory, jsonObj, "inventory");
	  // TODO: Handle serialization of ConsumptionListeners
	  effect = update(Effect.class, effect, jsonObj, "effect");
	}
	
	@Override
	protected void _writeJson(Json.Writer writer) {
	  super._writeJson(writer);
	  writer.value("moveSpeed", moveSpeed);
	  write(fieldOfView, writer, "fieldOfView");
	  write(hearingRange, writer, "hearingRange");
	  write(killedSound, writer, "killedSound");
	  writer.value("score", score);
	  writeEnum(faceDirection, writer, "faceDirection");
	  write(inventory, writer, "inventory");
    // TODO: Handle writes of ConsumptionListeners
	  write(effect, writer, "effect");
	}
	
	@Override
	protected void _write(EntityWriter entityWriter) {
	  super._write(entityWriter);
	  Json.Writer writer = entityWriter.getWriter();
    writer.value("moveSpeed", moveSpeed);
    write(fieldOfView, writer, "fieldOfView");
    write(hearingRange, writer, "hearingRange");
    write(killedSound, writer, "killedSound");
    writer.value("score", score);
    writeEnum(faceDirection, writer, "faceDirection");
    writeEntityHolder(inventory, entityWriter, "inventory");
    // TODO: Handle writes of ConsumptionListeners
    write(effect, writer, "effect");
	}
	*/
	
}
