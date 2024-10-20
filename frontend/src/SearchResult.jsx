import './assets/css/artist.css';
import { Link, useSearchParams } from 'react-router-dom';
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Pagination from 'react-bootstrap/Pagination';
//import "bootstrap/dist/css/bootstrap.min.css";

function SearchResult() {

  const [query, setQuery] = useSearchParams();
  var keyword = query.get('keyword');

  const [search, setSearch] = useState([]);
  
  useEffect(() => {
      const searchData = async() => {
      const searchInfo = await axios.get(`http://192.168.0.7/api/search/${keyword}`);
          setSearch(searchInfo.data);
          console.log(searchInfo.data);
      }

      searchData();
  }, [keyword])

  let len = Object.keys(search).length;
  var artistLen = 0;
  var artLen = 0;
  var exhLen = 0;
  
  console.log(len);

  if(len>0) {
    artistLen = search.artists.length;
    artLen = search.artworks.length;
    exhLen = search.exhibitions.length;
  }



  return (
    <div style={{marginTop: '30px', display:'flex', flexDirection:'column', alignItems:'center'}}>
        <div style={{marginBottom:'62px', width: 1140}}>
            <h2>[{keyword}] 검색결과</h2> 
            {len>0 && (artistLen || artLen || exhLen) ? (
              artistLen>0 ? 
              <div>
               {search.artists.map((item, index) => (
                  <div className="img_art"><Link to={`/artist/${item.token}`}><img src='https://via.placeholder.com/356x356' alt=""/></Link></div>
                ))}
              </div>
              : (
                artLen>0 ? 
                <div>
                {search.artworks.map((item, index) => (
                    <div className="img_art"><Link to={`/artwork/${item.token}`}><img src='https://via.placeholder.com/356x356' alt=""/></Link></div>
                  ))}
                </div>
                : <div>
                {search.exhibitions.map((item, index) => (
                  <div className="img_exh"><Link to={`/exhibition/${item.token}`}><img src='https://via.placeholder.com/261x356' alt=""/></Link></div>
                ))}
              </div>
             )
              /* {paginationBasic} */
            ) : (
                <h2 style={{color: '#9C9A97'}}>검색결과가 없습니다.</h2>
            )}
        </div>
    </div>
      
  )
}

export default SearchResult;