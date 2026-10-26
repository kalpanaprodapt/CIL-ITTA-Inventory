import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { ANTENNA_CRITICALITY_OPTIONS } from './types';
import type { AntennaFilter } from './types';

interface Props {
  value: AntennaFilter;
  onChange: (next: AntennaFilter) => void;
  onReset: () => void;
}

export function AntennaFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: AntennaFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Antenna filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Antenna"
          placeholder="code, name or description"
          value={value.q ?? ''}
          onChange={(v) => patch({ q: v || undefined })}
        />
      </FilterField>
      <FilterField label="Status">
        <MultiSelect
          ariaLabel="Status filter"
          options={LIFECYCLE_OPTIONS}
          value={value.status ?? []}
          onChange={(v) => patch({ status: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Code">
        <Input
          aria-label="Code filter"
          value={value.code ?? ''}
          onChange={(v) => patch({ code: v || undefined })}
        />
      </FilterField>
      <FilterField label="Name">
        <Input
          aria-label="Name filter"
          value={value.name ?? ''}
          onChange={(v) => patch({ name: v || undefined })}
        />
      </FilterField>
      <FilterField label="External Ref">
        <Input
          aria-label="External Ref filter"
          value={value.externalRef ?? ''}
          onChange={(v) => patch({ externalRef: v || undefined })}
        />
      </FilterField>
      <FilterField label="Sector Id">
        <Input
          aria-label="Sector Id filter"
          placeholder="UUID"
          value={value.sectorId ?? ''}
          onChange={(v) => patch({ sectorId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Model">
        <Input
          aria-label="Model filter"
          value={value.model ?? ''}
          onChange={(v) => patch({ model: v || undefined })}
        />
      </FilterField>
      <FilterField label="Height M">
        <RangeInputs
          ariaLabel="Height M"
          min={value.heightMMin}
          max={value.heightMMax}
          onChange={(min, max) => patch({ heightMMin: min, heightMMax: max })}
        />
      </FilterField>
      <FilterField label="Azimuth Deg">
        <RangeInputs
          ariaLabel="Azimuth Deg"
          min={value.azimuthDegMin}
          max={value.azimuthDegMax}
          onChange={(min, max) => patch({ azimuthDegMin: min, azimuthDegMax: max })}
        />
      </FilterField>
      <FilterField label="Tilt Deg">
        <RangeInputs
          ariaLabel="Tilt Deg"
          min={value.tiltDegMin}
          max={value.tiltDegMax}
          onChange={(min, max) => patch({ tiltDegMin: min, tiltDegMax: max })}
        />
      </FilterField>
      <FilterField label="Gain Dbi">
        <RangeInputs
          ariaLabel="Gain Dbi"
          min={value.gainDbiMin}
          max={value.gainDbiMax}
          onChange={(min, max) => patch({ gainDbiMin: min, gainDbiMax: max })}
        />
      </FilterField>
      <FilterField label="Source System">
        <Input
          aria-label="Source System filter"
          value={value.sourceSystem ?? ''}
          onChange={(v) => patch({ sourceSystem: v || undefined })}
        />
      </FilterField>
      <FilterField label="Region">
        <Input
          aria-label="Region filter"
          value={value.region ?? ''}
          onChange={(v) => patch({ region: v || undefined })}
        />
      </FilterField>
      <FilterField label="Owner">
        <Input
          aria-label="Owner filter"
          value={value.owner ?? ''}
          onChange={(v) => patch({ owner: v || undefined })}
        />
      </FilterField>
      <FilterField label="Criticality">
        <MultiSelect
          ariaLabel="Criticality filter"
          options={ANTENNA_CRITICALITY_OPTIONS}
          value={value.criticality ?? []}
          onChange={(v) => patch({ criticality: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Cost Center">
        <Input
          aria-label="Cost Center filter"
          value={value.costCenter ?? ''}
          onChange={(v) => patch({ costCenter: v || undefined })}
        />
      </FilterField>
      <div className="filter-bar__actions">
        <button type="button" className="btn btn--ghost" onClick={onReset}>
          Reset filters
        </button>
      </div>
    </form>
  );
}
