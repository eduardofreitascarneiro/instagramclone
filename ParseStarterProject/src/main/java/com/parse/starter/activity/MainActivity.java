/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;
import com.parse.starter.R;

import java.util.List;


public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    logOut();
    loginUsuario();

  }

  public void logOut(){
      ParseUser.logOut();
  }

  public void loginUsuario(){
     ParseUser.logInInBackground("eduardocarneiro", "123456", new LogInCallback() {
         @Override
         public void done(ParseUser user, ParseException e) {
             if ( e == null ){
                 Log.i("Resultado", "Usuario logado com sucesso");
                 Log.i("Resultado", user.getUsername());
             }else{
                 Log.i("Resultado", "Login falhou");
             }
         }
     });
  }

  public void verificarUsuarioLogado(){
//      ParseUser.logOut();
//      verificarUsuarioLogado();

      if ( ParseUser.getCurrentUser() != null ){
          Log.i("Resultado", "Usuario logado");
          Log.i("Resultado", ParseUser.getCurrentUser().getUsername().toString());

      }else{
          Log.i("Resultado", "Usuario não logado");
      }
  }


  public void salvarUsuario(String nome, String senha, String email){
      //salvarUsuario("eduardocarneiro", "123456", "eduardocarneiro@gmail.com");
      ParseUser usuario = new ParseUser();
      usuario.setUsername(nome);
      usuario.setPassword(senha);
      usuario.setEmail(email);
      usuario.signUpInBackground(new SignUpCallback() {
          @Override
          public void done(ParseException e) {
              if( e == null ){
                  Log.i("Resultado", "Sucesso");
              }else{
                  Log.i("Resultado", "Falha");
              }
          }
      });
  }


  public void filtrarDados(String noDesejado){

        //Exemplo filtrarDados("Roots");

        ParseQuery<ParseObject> filtro = ParseQuery.getQuery(noDesejado);

        //opcional: Adicionando condiução de filtro
        //filtro.whereGreaterThanOrEqualTo("idade",25);
        filtro.addAscendingOrder("nome");

        filtro.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e == null){
                    StringBuffer st = new StringBuffer();
                    for ( ParseObject obj : objects ){
                        Log.i("Resultado", "Nome: "+ obj.get("nome") + " Idade: "+ obj.get(    "idade") );
                    }

                }else{
                    Toast.makeText(MainActivity.this, "Erro ao consultar", Toast.LENGTH_SHORT).show();
                }
            }
        });

  }


  public void update(String noRoot, String idObject, final String atributo1, final String valoratributo1, final String atributo2, final Integer valorAtributo){
      //update("Roots", "aY9r0YQ89l", "nome", "olivia", "idade", 48);
      ParseQuery<ParseObject> consulta = ParseQuery.getQuery(noRoot);
      consulta.getInBackground(idObject, new GetCallback<ParseObject>() {
          @Override
          public void done(ParseObject object, ParseException e) {
              if ( e == null ){
                  object.put( atributo1, valoratributo1);
                  object.put( atributo2, valorAtributo);
                  object.saveInBackground(new SaveCallback() {
                      @Override
                      public void done(ParseException e) {
                          if( e == null ){
                              Toast.makeText(MainActivity.this, "Sucesso ao alterar registro", Toast.LENGTH_SHORT).show();
                          }else{
                              Toast.makeText(MainActivity.this, "Erro ao alterar registro", Toast.LENGTH_SHORT).show();
                          }
                      }
                  });
              }else{
                  Toast.makeText(MainActivity.this, "Falha ao consultar registro", Toast.LENGTH_SHORT).show();
              }
          }
      });
  }



  public void salvar(String noRaiz, String param_1, String valorparam_1, String param_2, Integer valorObjeto ){

    //Exemplo: salvar("Root", "nome", "Carneiro", "idade", 25 );

    ParseObject pontuacao = new ParseObject(noRaiz);
    pontuacao.put(param_1, valorparam_1);
    pontuacao.put(param_2, valorObjeto);
    pontuacao.saveInBackground(new SaveCallback() {
      @Override
      public void done(ParseException e) {
        if ( e == null ){
          Toast.makeText(MainActivity.this, "Sucesso", Toast.LENGTH_SHORT).show();
        }else{
          Toast.makeText(MainActivity.this, "Falha", Toast.LENGTH_SHORT).show();
        }
      }
    });
  }


}
