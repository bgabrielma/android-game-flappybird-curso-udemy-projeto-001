package com.bgabrielma.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

public class FlappyBird extends ApplicationAdapter {

	// Classe responsável pelas animações
	private SpriteBatch batch;
	private Texture[] passaro;
	private Texture canoBaixo, canoTopo, fundo;

	//Atributos de configuração do ecrã
	private int larguraDispositivo;
	private int alturaDispositivo;
	private int estadoJogo = 0;
	private int pontuacao = 0;

	private float variacao = 0;
	private float velocidadeQueda = 0;
	private float posicaoInicialVertical;
	private float posicaoMovimentoCanoHorizontal;
	private float espacoEntreCanos;
	private float deltaTime;
	private float alturaEntreCanosRandomica;

	private Random numeroRandomico;

	private BitmapFont fonte;

	private boolean marcouPonto;

	@Override
	public void create () {
		batch = new SpriteBatch();
		numeroRandomico = new Random();
		fonte = new BitmapFont();
		fonte.setColor(Color.WHITE);
		fonte.getData().setScale(5);

		passaro = new Texture[3];
		passaro[0] = new Texture("passaro1.png");
		passaro[1] = new Texture("passaro2.png");
		passaro[2] = new Texture("passaro3.png");

		fundo = new Texture("fundo.png");
		canoBaixo = new Texture("cano_baixo.png");
		canoTopo = new Texture("cano_topo.png");

		larguraDispositivo = Gdx.graphics.getWidth();
		alturaDispositivo = Gdx.graphics.getHeight();

		posicaoInicialVertical = alturaDispositivo / 2;

		posicaoMovimentoCanoHorizontal = larguraDispositivo;
		espacoEntreCanos = 300;

		Gdx.graphics.setVSync(true);
	}

	@Override
	public void render () {
		deltaTime = Gdx.graphics.getDeltaTime();
		variacao += deltaTime * 10;

		if(variacao > 2) variacao = 0;

		if(estadoJogo == 0) {
			if(Gdx.input.justTouched()) {
				estadoJogo = 1;
			}
		} else {
			posicaoMovimentoCanoHorizontal -= deltaTime * 1000;

			if (Gdx.input.justTouched()) {
				velocidadeQueda = -15;
			}
			if(posicaoInicialVertical > 0 || velocidadeQueda < 0)
				posicaoInicialVertical -= ++velocidadeQueda ;
			if(posicaoMovimentoCanoHorizontal < -canoTopo.getWidth()) {
				posicaoMovimentoCanoHorizontal = larguraDispositivo;
				alturaEntreCanosRandomica = numeroRandomico.nextInt(401) - 200;
				marcouPonto = false;
			}
			if(posicaoMovimentoCanoHorizontal < 120) {
				if(!marcouPonto) {
					pontuacao ++;
					marcouPonto = true;
				}
			}
		}
		batch.begin();

		batch.draw(fundo, 0, 0,larguraDispositivo, alturaDispositivo);
		batch.draw(canoTopo, posicaoMovimentoCanoHorizontal, alturaDispositivo / 2 + espacoEntreCanos / 2 + alturaEntreCanosRandomica);
		batch.draw(canoBaixo, posicaoMovimentoCanoHorizontal, alturaDispositivo / 2 - canoBaixo.getHeight() - espacoEntreCanos / 2 + alturaEntreCanosRandomica);
		batch.draw( passaro[(int) variacao], 120, posicaoInicialVertical);
		fonte.draw(batch, "Conas comidas: " + pontuacao, larguraDispositivo / 2, alturaDispositivo - 100, 0, 1, false);
		batch.end();
	}
	
	@Override
	public void dispose () { }


}
