import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { LINE_CARD_CRITICALITY_OPTIONS } from './types';
import type { LineCardFilter } from './types';

interface Props {
  value: LineCardFilter;
  onChange: (next: LineCardFilter) => void;
  onReset: () => void;
}

export function LineCardFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: LineCardFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Line Card filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Line Card"
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
      <FilterField label="Chassis Id">
        <Input
          aria-label="Chassis Id filter"
          placeholder="UUID"
          value={value.chassisId ?? ''}
          onChange={(v) => patch({ chassisId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Slot Number">
        <RangeInputs
          ariaLabel="Slot Number"
          min={value.slotNumberMin}
          max={value.slotNumberMax}
          onChange={(min, max) => patch({ slotNumberMin: min, slotNumberMax: max })}
        />
      </FilterField>
      <FilterField label="Card Type">
        <Input
          aria-label="Card Type filter"
          value={value.cardType ?? ''}
          onChange={(v) => patch({ cardType: v || undefined })}
        />
      </FilterField>
      <FilterField label="Port Count">
        <RangeInputs
          ariaLabel="Port Count"
          min={value.portCountMin}
          max={value.portCountMax}
          onChange={(min, max) => patch({ portCountMin: min, portCountMax: max })}
        />
      </FilterField>
      <FilterField label="Serial Number">
        <Input
          aria-label="Serial Number filter"
          value={value.serialNumber ?? ''}
          onChange={(v) => patch({ serialNumber: v || undefined })}
        />
      </FilterField>
      <FilterField label="Firmware">
        <Input
          aria-label="Firmware filter"
          value={value.firmware ?? ''}
          onChange={(v) => patch({ firmware: v || undefined })}
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
          options={LINE_CARD_CRITICALITY_OPTIONS}
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
