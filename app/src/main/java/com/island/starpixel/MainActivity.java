package com.island.starpixel;
import com.island.*;
import android.os.*;
import android.widget.*;
import com.island.starpixel.lingue.*;
import android.graphics.*;
import android.view.*;
import android.view.View.*;
import com.island.starpixel.blocchi.*;
import java.io.*;
import java.net.*;
public class MainActivity extends Schermo
{
	//S5 MINI 1.4GHZ * 4 5.6ghz
	//S2 1.9 * 4 1.3 * 4 12.8ghz
	//S4 2.3*4 9.2ghz
	//p8lite2017 2.1*4 1.7*4 15.2 ghz
	//S3 MINI 1 GHZ *2 2GHZ
	//differenza S2 S5mini 7.2ghz * 1000000
	//private static Server server=new Server("connessione.hopto.org",30000);
	public void display(Point p)
	{
		super.display(p);
		//s5miniDenso(p);
		//displayPiccolo(p);
		//s4(p);
		//displayDenso(p);
		//p8lite2017denso(p);
		//s3mini(p);
	}
	public void s5mini(Point p)
	{
		displayPiccolo(p,720,1280,326);
	}
	public void s3mini(Point p)
	{
		displayPiccolo(p,480,800,234);
	}
	public int s5minivelocita()
	{
		return 7200000;
	}
	public int s3minivelocita()
	{
		return 10800000;
	}
	public void s4Denso(Point p)
	{
		p.x=1080;
		p.y=1920;
	}
	public void s3miniDenso(Point p)
	{
		p.x=480;
		p.y=800;
	}
	public void s5miniDenso(Point p)
	{
		p.x=720;
		p.y=1280;
	}
	public void s4(Point p)
	{
		displayPiccolo(p,1080,1920,442);
	}
	public void p8lite2017(Point p)
	{
		displayPiccolo(p,1080,1920,424);
	}
	public void p8lite2017denso(Point p)
	{
		p.x=1080;
		p.y=1920;
	}
	public int p8lite2017velocita()
	{
		return 0;
	}
	public int backflipvelocita()
	{
		return 12272000;
	}
	public boolean debug()
	{
		return true;
	}
	public boolean suoni()
	{
		return false;
	}
	public boolean immagini()
	{
		return true;
	}
	public boolean debugger()
	{
		return true;
	}
	public int rallentamento()
	{
		return super.rallentamento();
		//return s5minivelocita();
		//return p8lite2017velocita();
		//return s3minivelocita();
		//return backflipvelocita();
	}
	public int rallentamentoConnessione()
	{
		return super.rallentamentoConnessione();
		//return g3();
		//return g();
	}
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		toast(""+Lista.arrotonda(120.987654321,2));
		//server.schermo(this);
		font(Typeface.SERIF,Typeface.BOLD_ITALIC);
		int[]dim=dimensioni();
		int x=dim[0]/blocchi.vista;
		int y=dim[1]/blocchi.vista;
		carica(R.drawable.rottura1,x,y);
		carica(R.drawable.rottura2,x,y);
		carica(R.drawable.rottura3,x,y);
		Gruppo g=new Gruppo(this,10,10);
		new Testo(g,0,0,10,5,"STAR PIXEL").carattere(0.5);
		new Bottone(g,1,6,9,7).scrivi(lingua.gioca).setOnClickListener(gioca());
		new Bottone(g,1,8,9,9).scrivi(lingua.aiuto).setOnClickListener(aiuto());
		musica=new Suono(this,R.raw.skyward).infinito(true).start();
		g.aggiorna();
	}
	Lingua lingua=Lingua.lingua();
	Blocchi blocchi=new Blocchi();
	String cartella=Environment.getExternalStorageDirectory().getPath()+File.separator+"STAR PIXEL"+File.separator;
	Suono musica;
	protected void onSaveInstanceState(Bundle b)
	{
		b.putInt("MUSICA",musica.posizione());
		super.onSaveInstanceState(b);
	}
	protected void onRestoreInstanceState(Bundle b)
	{
		musica.posizione(b.getInt("MUSICA"));
		if(b.getBoolean("AIUTO"))new Aiuto(this).leggi(b);
		if(b.getBoolean("MENU"))new Menu(this).leggi(b);
		if(b.getBoolean("DESCRIZIONE"))new Descrizione(this,blocchi.blocchi[b.getInt("DESCRIZIONE BLOCCO")]).leggi(b);
		if(b.getBoolean("NUOVO"))new Nuovo(this).leggi(b);
		if(b.getBoolean("SCHERMATA"))new Schermata(this,b.getString("SCHERMATA SERVER"),b.getString("SCHERMATA CARTELLA"));
		super.onRestoreInstanceState(b);
	}
	protected void onDestroy()
	{
		lingua=null;
		blocchi=null;
		cartella=null;
		musica=null;
		super.onDestroy();
	}
	private View.OnClickListener gioca()
	{
		return new View.OnClickListener()
		{
			public void onClick(View p1)
			{
				new Menu(schermo());
				toast(""+finestre.size());
			}
		};
	}
	private View.OnClickListener aiuto()
	{
		return new View.OnClickListener()
		{
			public void onClick(View p1)
			{
				new Aiuto(schermo());
			}
		};
	}
}
