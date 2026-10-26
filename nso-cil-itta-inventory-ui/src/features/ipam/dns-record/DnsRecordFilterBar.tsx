import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { DNS_RECORD_RECORD_TYPE_OPTIONS, DNS_RECORD_CRITICALITY_OPTIONS } from './types';
import type { DnsRecordFilter } from './types';

interface Props {
  value: DnsRecordFilter;
  onChange: (next: DnsRecordFilter) => void;
  onReset: () => void;
}

export function DnsRecordFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: DnsRecordFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Dns Record filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Dns Record"
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
      <FilterField label="Zone Name">
        <Input
          aria-label="Zone Name filter"
          value={value.zoneName ?? ''}
          onChange={(v) => patch({ zoneName: v || undefined })}
        />
      </FilterField>
      <FilterField label="Record Type">
        <MultiSelect
          ariaLabel="Record Type filter"
          options={DNS_RECORD_RECORD_TYPE_OPTIONS}
          value={value.recordType ?? []}
          onChange={(v) => patch({ recordType: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Record Value">
        <Input
          aria-label="Record Value filter"
          value={value.recordValue ?? ''}
          onChange={(v) => patch({ recordValue: v || undefined })}
        />
      </FilterField>
      <FilterField label="Ttl">
        <RangeInputs
          ariaLabel="Ttl"
          min={value.ttlMin}
          max={value.ttlMax}
          onChange={(min, max) => patch({ ttlMin: min, ttlMax: max })}
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
          options={DNS_RECORD_CRITICALITY_OPTIONS}
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
