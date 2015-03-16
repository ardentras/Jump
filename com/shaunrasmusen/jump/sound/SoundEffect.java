package com.shaunrasmusen.jump.sound;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL10;
import org.lwjgl.util.WaveData;

public class SoundEffect {

	public String file;
	private boolean playing;
	private float gain, pitch;

	public static IntBuffer buffer = BufferUtils.createIntBuffer(1);
	public static IntBuffer source = BufferUtils.createIntBuffer(1);
	public FloatBuffer sourcePos = (FloatBuffer) BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f }).rewind();
	public FloatBuffer sourceVel = (FloatBuffer) BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f }).rewind();
	public FloatBuffer listenerPos = (FloatBuffer) BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f }).rewind();
	public FloatBuffer listenerVel = (FloatBuffer) BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f }).rewind();
	public FloatBuffer listenerOri = (FloatBuffer) BufferUtils.createFloatBuffer(6).put(new float[] { 0.0f, 0.0f, -1.0f, 0.0f, 1.0f, 0.0f }).rewind();

	public SoundEffect(String file, float gain, float pitch) {
		this.file = file;
		this.gain = gain;
		this.pitch = pitch;
	}

	public int loadSound() {
		AL10.alGenBuffers(buffer);

		if (AL10.alGetError() != AL10.AL_NO_ERROR) return AL10.AL_FALSE;

		WaveData wavefile = WaveData.create("res/sounds/" + file + ".wav");
		AL10.alBufferData(buffer.get(0), wavefile.format, wavefile.data, wavefile.samplerate);
		wavefile.dispose();

		AL10.alGenSources(source);

		if (AL10.alGetError() != AL10.AL_NO_ERROR) return AL10.AL_FALSE;

		AL10.alSourcei(source.get(0), AL10.AL_BUFFER, buffer.get(0));
		AL10.alSourcef(source.get(0), AL10.AL_PITCH, pitch);
		AL10.alSourcef(source.get(0), AL10.AL_GAIN, gain);
		AL10.alSource(source.get(0), AL10.AL_POSITION, sourcePos);
		AL10.alSource(source.get(0), AL10.AL_VELOCITY, sourceVel);

		if (AL10.alGetError() == AL10.AL_NO_ERROR) return AL10.AL_TRUE;

		return AL10.AL_FALSE;
	}

	public void setListenerValues() {
		AL10.alListener(AL10.AL_POSITION, listenerPos);
		AL10.alListener(AL10.AL_VELOCITY, listenerVel);
		AL10.alListener(AL10.AL_ORIENTATION, listenerOri);
	}

	public void playSound() {
		playing = true;
		AL10.alGetError();

		if (loadSound() == AL10.AL_FALSE) {
			System.out.println("Error loading data.");
			return;
		}

		setListenerValues();
		AL10.alSourcePlay(source.get(0));
	}

	public void stopSound() {
		playing = false;
		AL10.alSourceStop(source.get(0));
		AL10.alDeleteSources(SoundEffect.source);
		AL10.alDeleteBuffers(SoundEffect.buffer);
	}

	public void pauseSound() {
		AL10.alSourcePause(source.get(0));
	}

	public void setSourcePos(int x, int y) {
		sourcePos.put(0, x);
		sourcePos.put(1, y);
	}

	public void setListenerPos(int x, int y) {
		listenerPos.put(0, x);
		listenerPos.put(1, y);
	}

	public boolean isPlaying() {
		return playing;
	}
}
