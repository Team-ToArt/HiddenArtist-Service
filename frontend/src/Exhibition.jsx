import { Link } from 'react-router-dom';
import './assets/css/exhibition.css';
import { Swiper, SwiperSlide } from 'swiper/react';
import 'swiper/css';

function App() {

  const titleArr = ['진행중인 전시회', '오픈 예정 전시회', '마감된 전시회'];
  const testArr = ['1', '2', '3', '4'];

  return (
    <>
      <div style={{marginTop:'62px', marginBottom:'24px', display: 'flex', flexDirection:'column', alignItems:'center'}}>
      {titleArr.map((category, index) => (
        <div>
          <h2>{category}</h2>
          {testArr.map((idx) => (
            <Link to={idx}>
              <div class={"exh_"+index} style={{display: 'inline-block'}}>
                <div class="img_exh"><img src="https://pimg.mk.co.kr/meet/neds/2017/09/image_readmed_2017_601694_15047650883020840.jpg" alt=""/></div>
                <div class='exhTitle'>전시회 명</div>
                <div class='exhPeriod'>2024.09.20~2024.10.20</div>
              </div>
            </Link>
          ))}
        </div>
      ))}
      </div>
    </>
  )
}

export default App
