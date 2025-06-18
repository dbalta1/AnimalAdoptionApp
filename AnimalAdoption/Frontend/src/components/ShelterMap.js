import React from 'react';
import { MapContainer, TileLayer, Marker, Popup } from 'react-leaflet';
import L from 'leaflet';

// Podaci o skloništima
const shelters = [
  {
    id: 1,
    name: 'Azil Sarajevo',
    position: [43.8563, 18.4131],
    description: 'Sklonište za napuštene pse i mačke u Sarajevu',
    address: 'Adresa: Obala Kulina bana bb, Sarajevo'
  },
  {
    id: 2,
    name: 'Azil Zenica',
    position: [44.2017, 17.9075],
    description: 'Azil za životinje u Zenici',
    address: 'Adresa: Put do azila 12, Zenica'
  },
  {
    id: 3,
    name: 'Azil Tuzla',
    position: [44.5378, 18.6706],
    description: 'Tuzlansko sklonište za napuštene životinje',
    address: 'Adresa: Azilski put 5, Tuzla'
  }
];

// Crveni marker
const customIcon = new L.Icon({
  iconUrl: 'https://raw.githubusercontent.com/pointhi/leaflet-color-markers/master/img/marker-icon-red.png',
  iconSize: [25, 41],
  iconAnchor: [12, 41],
  popupAnchor: [1, -34],
  shadowUrl: 'https://unpkg.com/leaflet@1.7.1/dist/images/marker-shadow.png',
  shadowSize: [41, 41]
});

const ShelterMap = () => {
  return (
    <div style={{ height: '500px', width: '100%' }}>
      <MapContainer
        center={[44.2, 18.3]}
        zoom={7}
        scrollWheelZoom={true}
        style={{ height: '100%', width: '100%' }}
      >
        {/* Detaljna mapa s ulicama */}
        <TileLayer
          attribution='Map data &copy; <a href="https://carto.com/">CARTO</a>'
          url="https://{s}.basemaps.cartocdn.com/light_all/{z}/{x}/{y}{r}.png"
        />

        {/* Marker za svako sklonište */}
        {shelters.map(shelter => (
          <Marker key={shelter.id} position={shelter.position} icon={customIcon}>
            <Popup>
              <strong>{shelter.name}</strong><br />
              {shelter.description}<br />
              {shelter.address}
            </Popup>
          </Marker>
        ))}
      </MapContainer>
    </div>
  );
};

export default ShelterMap;
