import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { INTERFACE_OPER_STATE_OPTIONS, INTERFACE_CRITICALITY_OPTIONS } from './types';
import type { InterfaceFilter } from './types';

interface Props {
  value: InterfaceFilter;
  onChange: (next: InterfaceFilter) => void;
  onReset: () => void;
}

export function InterfaceFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: InterfaceFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Interface filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Interface"
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
      <FilterField label="Port Id">
        <Input
          aria-label="Port Id filter"
          placeholder="UUID"
          value={value.portId ?? ''}
          onChange={(v) => patch({ portId: v || undefined })}
        />
      </FilterField>
      <FilterField label="If Name">
        <Input
          aria-label="If Name filter"
          value={value.ifName ?? ''}
          onChange={(v) => patch({ ifName: v || undefined })}
        />
      </FilterField>
      <FilterField label="If Index">
        <RangeInputs
          ariaLabel="If Index"
          min={value.ifIndexMin}
          max={value.ifIndexMax}
          onChange={(min, max) => patch({ ifIndexMin: min, ifIndexMax: max })}
        />
      </FilterField>
      <FilterField label="Mac Address">
        <Input
          aria-label="Mac Address filter"
          value={value.macAddress ?? ''}
          onChange={(v) => patch({ macAddress: v || undefined })}
        />
      </FilterField>
      <FilterField label="Mtu">
        <RangeInputs
          ariaLabel="Mtu"
          min={value.mtuMin}
          max={value.mtuMax}
          onChange={(min, max) => patch({ mtuMin: min, mtuMax: max })}
        />
      </FilterField>
      <FilterField label="Oper State">
        <MultiSelect
          ariaLabel="Oper State filter"
          options={INTERFACE_OPER_STATE_OPTIONS}
          value={value.operState ?? []}
          onChange={(v) => patch({ operState: v.length ? v : undefined })}
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
          options={INTERFACE_CRITICALITY_OPTIONS}
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
