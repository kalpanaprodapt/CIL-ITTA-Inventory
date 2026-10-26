import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { SECTOR_CRITICALITY_OPTIONS } from './types';
import type { SectorFilter } from './types';

interface Props {
  value: SectorFilter;
  onChange: (next: SectorFilter) => void;
  onReset: () => void;
}

export function SectorFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: SectorFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Sector filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Sector"
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
      <FilterField label="Base Station Id">
        <Input
          aria-label="Base Station Id filter"
          placeholder="UUID"
          value={value.baseStationId ?? ''}
          onChange={(v) => patch({ baseStationId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Sector Number">
        <RangeInputs
          ariaLabel="Sector Number"
          min={value.sectorNumberMin}
          max={value.sectorNumberMax}
          onChange={(min, max) => patch({ sectorNumberMin: min, sectorNumberMax: max })}
        />
      </FilterField>
      <FilterField label="Pci">
        <RangeInputs
          ariaLabel="Pci"
          min={value.pciMin}
          max={value.pciMax}
          onChange={(min, max) => patch({ pciMin: min, pciMax: max })}
        />
      </FilterField>
      <FilterField label="Frequency Mhz">
        <RangeInputs
          ariaLabel="Frequency Mhz"
          min={value.frequencyMhzMin}
          max={value.frequencyMhzMax}
          onChange={(min, max) => patch({ frequencyMhzMin: min, frequencyMhzMax: max })}
        />
      </FilterField>
      <FilterField label="Bandwidth Mhz">
        <RangeInputs
          ariaLabel="Bandwidth Mhz"
          min={value.bandwidthMhzMin}
          max={value.bandwidthMhzMax}
          onChange={(min, max) => patch({ bandwidthMhzMin: min, bandwidthMhzMax: max })}
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
          options={SECTOR_CRITICALITY_OPTIONS}
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
      <FilterField label="Effective From">
        <DateRange
          ariaLabel="Effective From"
          from={value.effectiveFromFrom}
          to={value.effectiveFromTo}
          onChange={(from, to) => patch({ effectiveFromFrom: from, effectiveFromTo: to })}
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
