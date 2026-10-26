import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { RACK_UNIT_FACE_OPTIONS, RACK_UNIT_CRITICALITY_OPTIONS } from './types';
import type { RackUnitFilter } from './types';

interface Props {
  value: RackUnitFilter;
  onChange: (next: RackUnitFilter) => void;
  onReset: () => void;
}

export function RackUnitFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: RackUnitFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Rack Unit filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Rack Unit"
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
      <FilterField label="Rack Id">
        <Input
          aria-label="Rack Id filter"
          placeholder="UUID"
          value={value.rackId ?? ''}
          onChange={(v) => patch({ rackId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Unit Number">
        <RangeInputs
          ariaLabel="Unit Number"
          min={value.unitNumberMin}
          max={value.unitNumberMax}
          onChange={(min, max) => patch({ unitNumberMin: min, unitNumberMax: max })}
        />
      </FilterField>
      <FilterField label="Occupied">
        <TriState
          ariaLabel="Occupied filter"
          value={value.occupied}
          onChange={(v) => patch({ occupied: v })}
        />
      </FilterField>
      <FilterField label="Mounted Device Id">
        <Input
          aria-label="Mounted Device Id filter"
          placeholder="UUID"
          value={value.mountedDeviceId ?? ''}
          onChange={(v) => patch({ mountedDeviceId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Face">
        <MultiSelect
          ariaLabel="Face filter"
          options={RACK_UNIT_FACE_OPTIONS}
          value={value.face ?? []}
          onChange={(v) => patch({ face: v.length ? v : undefined })}
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
          options={RACK_UNIT_CRITICALITY_OPTIONS}
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
