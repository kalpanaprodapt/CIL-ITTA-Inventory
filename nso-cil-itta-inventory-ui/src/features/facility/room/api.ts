import { http } from '@/shared/api/http';
import type { LifecycleStatus, Page } from '@/shared/types';
import { ROOM_PATH } from './types';
import type { Room, RoomFilter, RoomInput, RoomSummary, RoomUpdateInput } from './types';
import { toRoomQuery } from './filterState';

export interface BulkResult<T> {
  succeeded: T[];
  failures: { index: number; message: string }[];
}

export function searchRooms(
  filter: RoomFilter,
  page = 0,
  size = 20,
  sort?: string,
): Promise<Page<Room>> {
  return http.get<Page<Room>>(ROOM_PATH, { ...toRoomQuery(filter), page, size, sort });
}

export function getRoom(id: string): Promise<Room> {
  return http.get<Room>(`${ROOM_PATH}/${id}`);
}

export function getRoomByCode(code: string): Promise<Room> {
  return http.get<Room>(`${ROOM_PATH}/by-code/${encodeURIComponent(code)}`);
}

export function listRoomSummaries(status?: LifecycleStatus): Promise<RoomSummary[]> {
  return http.get<RoomSummary[]>(`${ROOM_PATH}/summaries`, { status });
}

export function createRoom(input: RoomInput): Promise<Room> {
  return http.post<Room>(ROOM_PATH, input);
}

export function updateRoom(id: string, input: RoomUpdateInput): Promise<Room> {
  return http.put<Room>(`${ROOM_PATH}/${id}`, input);
}

export function changeRoomStatus(id: string, status: LifecycleStatus): Promise<Room> {
  return http.patch<Room>(`${ROOM_PATH}/${id}/status`, undefined, { status });
}

export function deleteRoom(id: string): Promise<void> {
  return http.delete(`${ROOM_PATH}/${id}`);
}

export function bulkCreateRooms(inputs: RoomInput[]): Promise<BulkResult<Room>> {
  return http.post<BulkResult<Room>>(`${ROOM_PATH}/bulk`, inputs);
}

export function getRoomStatusStats(): Promise<Record<LifecycleStatus, number>> {
  return http.get<Record<LifecycleStatus, number>>(`${ROOM_PATH}/stats/status`);
}

export function exportRoomsCsv(filter: RoomFilter): Promise<Blob> {
  return http.blob(`${ROOM_PATH}/export`, toRoomQuery(filter));
}

export function listRoomByFloorId(floorId: string, page = 0, size = 20): Promise<Page<Room>> {
  return http.get<Page<Room>>(`${ROOM_PATH}/by-floor-id/${floorId}`, { page, size });
}
