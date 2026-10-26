import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { CITY_PATH } from './types';
import type { City, CityFilter, CityInput, CitySummary, CityUpdateInput } from './types';
import { toCityQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchCitys(
  filter: CityFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<City>> {
  return http.get<Page<City>>(CITY_PATH, { ...toCityQuery(filter), page, size, sort });
}

export function getCity(id: string): Promise<City> {
  return http.get<City>(`${CITY_PATH}/${id}`);
}

export function getCityByCode(code: string): Promise<City> {
  return http.get<City>(`${CITY_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listCitySummaries(status?: LifecycleStatus): Promise<CitySummary[]> {
  return http.get<CitySummary[]>(`${CITY_PATH}/summaries`, { status });
}

export function createCity(input: CityInput): Promise<City> {
  return http.post<City>(CITY_PATH, input);
}

export function updateCity(id: string, input: CityUpdateInput): Promise<City> {
  return http.put<City>(`${CITY_PATH}/${id}`, input);
}

export function changeCityStatus(id: string, status: LifecycleStatus): Promise<City> {
  return http.patch<City>(`${CITY_PATH}/${id}/status`, undefined, { status });
}

export function deleteCity(id: string): Promise<void> {
  return http.delete(`${CITY_PATH}/${id}`);
}

export function bulkCreateCitys(inputs: CityInput[]): Promise<BulkResult<City>> {
  return http.post<BulkResult<City>>(`${CITY_PATH}/bulk`, inputs);
}

export function getCityStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${CITY_PATH}/stats/status`);
}

export function exportCitysCsv(filter: CityFilter): Promise<Blob> {
  return http.blob(`${CITY_PATH}/export`, toCityQuery(filter));
}

export function listCityByRegionId(regionId: string, page = 0, size = 20): Promise<Page<City>> {
  return http.get<Page<City>>(`${CITY_PATH}/by-region-id/${regionId}`, { page, size });
}
