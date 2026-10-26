import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { FIBER_STRAND_CRITICALITY_OPTIONS } from './types';
import type { FiberStrandFilter } from './types';

interface Props {
  value: FiberStrandFilter;
  onChange: (next: FiberStrandFilter) => void;
  onReset: () => void;
}

export function FiberStrandFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: FiberStrandFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Fiber Strand filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Fiber Strand"
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
      <FilterField label="Cable Id">
        <Input
          aria-label="Cable Id filter"
          placeholder="UUID"
          value={value.cableId ?? ''}
          onChange={(v) => patch({ cableId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Strand Number">
        <RangeInputs
          ariaLabel="Strand Number"
          min={value.strandNumberMin}
          max={value.strandNumberMax}
          onChange={(min, max) => patch({ strandNumberMin: min, strandNumberMax: max })}
        />
      </FilterField>
      <FilterField label="Buffer Tube">
        <Input
          aria-label="Buffer Tube filter"
          value={value.bufferTube ?? ''}
          onChange={(v) => patch({ bufferTube: v || undefined })}
        />
      </FilterField>
      <FilterField label="Tube Color">
        <Input
          aria-label="Tube Color filter"
          value={value.tubeColor ?? ''}
          onChange={(v) => patch({ tubeColor: v || undefined })}
        />
      </FilterField>
      <FilterField label="Attenuation Db">
        <RangeInputs
          ariaLabel="Attenuation Db"
          min={value.attenuationDbMin}
          max={value.attenuationDbMax}
          onChange={(min, max) => patch({ attenuationDbMin: min, attenuationDbMax: max })}
        />
      </FilterField>
      <FilterField label="In Use">
        <TriState
          ariaLabel="In Use filter"
          value={value.inUse}
          onChange={(v) => patch({ inUse: v })}
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
          options={FIBER_STRAND_CRITICALITY_OPTIONS}
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
