import { Link, Outlet,useNavigate } from 'react-router-dom';
import React, { useState, useEffect } from "react";
import axios from 'axios';

import { Input } from 'semantic-ui-react'

import '/src/assets/css/header.css';
import 'semantic-ui-css/semantic.min.css'

function Header() {

  const nav = useNavigate();
  
  const activeEnter = (e) => {
    let keyword = e.target.value.toLowerCase();

    if(e.key === "Enter") {
      if(keyword.length>0) { nav(`/search?keyword=${keyword}`); }
    }
  }

  
  
  return (
    <>
      <div style={{display:'flex',margin: '15px auto', justifyContent:'center'}}>
        <div id="logo"><Link to='/'><img src="/image/logo_fix_w.png" alt=""/></Link></div>

        <div id="search">
            <Input icon='search'
                size='huge'
                placeholder='Search by artist, gallery, style, theme, tag, etc...'
                onKeyDown={(e) => activeEnter(e)}
            />       
        </div>


        <button id="loginBtn">Login</button>
        <button id="signupBtn">Sign Up</button>
    </div>

    <div id="menu">
        <div><Link to='artist?p=1'>작가</Link></div>
        {/* <div><Link to='artworks'>작품</Link></div> */}
        <div><Link to='exhibition'>전시회</Link></div>
        <div><Link to='mentoring'>멘토링</Link></div>
        
    </div>
    
    <div style={{width: '100%', height: 1, background: 'gray'}}></div>

    <Outlet/>
    
    </>
  )
}

export default Header;
