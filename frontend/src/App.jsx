import { createBrowserRouter, Route, Routes } from "react-router-dom";
import Header from './Header.jsx'
import Home from './Home.jsx'
import Artist from './Artist.jsx'
import Exhibition from './Exhibition.jsx'
import ExhDetail from './ExhDetail.jsx'

function App() {

  return (
    <div>
      
      <Routes>
        <Route element={<Header />}>
          <Route path="" element={<Home />} />
          <Route path="artist" element={<Artist />} />
          <Route path="artworks" element={<Artist />} />
          <Route path="exhibition" element={<Exhibition />} />
          <Route path="exhibition/:exhID" element={<ExhDetail />} />
          <Route path="mentoring" element={<Artist />} />
        </Route>
      </Routes>
    </div>
  );
}
export default App;